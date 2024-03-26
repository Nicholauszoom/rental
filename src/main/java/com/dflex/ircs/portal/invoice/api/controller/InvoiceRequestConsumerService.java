package com.dflex.ircs.portal.invoice.api.controller;

import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.dflex.ircs.portal.auth.dto.CommunicationApiDetailsDto;
import com.dflex.ircs.portal.auth.service.CommunicationApiService;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceSubmissionApiAckBodyDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceSubmissionApiAckDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceSubmissionApiReqBodyDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceSubmissionApiReqDetailDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceSubmissionApiReqDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceSubmissionApiReqHeaderDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceSubmissionApiReqServiceDto;
import com.dflex.ircs.portal.invoice.entity.Invoice;
import com.dflex.ircs.portal.invoice.entity.InvoiceItem;
import com.dflex.ircs.portal.invoice.service.InvoiceItemService;
import com.dflex.ircs.portal.invoice.service.InvoiceService;
import com.dflex.ircs.portal.setup.entity.ServiceDepartment;
import com.dflex.ircs.portal.setup.entity.WorkStation;
import com.dflex.ircs.portal.setup.service.WorkStationService;
import com.dflex.ircs.portal.util.Constants;
import com.dflex.ircs.portal.util.PKIUtils;
import com.dflex.ircs.portal.util.Response;
import com.dflex.ircs.portal.util.Utils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;


/**
 * 
 * @author Augustino Mwageni
 * Payment Notification to Service Institution and Payment Facilitator
 */
@Component
public class InvoiceRequestConsumerService {

	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private InvoiceItemService invoiceItemService;
	
	@Autowired
	private WorkStationService workStationService;
	
	@Autowired
	private Utils utils;
	
	@Autowired
	private PKIUtils pkiUtils;

	@Autowired
	private CommunicationApiService communicationApiService;

	@Value("${rabbitmq.ircs.outgoing.exchange}")
	private String messageOutExchange;
	
	@Value("${rabbitmq.ircs.outgoing.retry.exchange}")
	private String messageOutRetryExchange;
	
	@Value("${rabbitmq.ircs.outgoing.pause.exchange}")
	private String messageOutPauseExchange;

	/**
	 * Process Invoice Submission Request
	 * @param data
	 * @param queue
	 * @param routingKey
	 * @param retryCounter
	 * @param retryStatusCode
	 * @param receivedTime
	 * @param processRequestId
	 * @param requestId
	 * @param clientKey
	 * @param clientCode
	 * @param clientId
	 * @param apiVersion
	 */
	@RabbitListener(id = "process_si_invoice_request", queues = "#{'${rabbitmq.ircs.si.invoice.outgoing.queues}'.split(',')}")
	public void processInvoiceSubmissionRequest(byte[] data, @Header(AmqpHeaders.CONSUMER_QUEUE) String queue,
			@Header("amqp_receivedRoutingKey") String routingKey, @Header("retryCounter") final String retryCounter,
			@Header("retryStatusCode") final String retryStatusCode, @Header("receivedTime") final String receivedTime,
			@Header("processRequestId") final String processRequestId, @Header("requestId") final String requestId,
			@Header("clientKey") final String clientKey,@Header("clientCode") final String clientCode,
			@Header("apiVersion") final Long apiVersion) {
		
		Map<String, String> amqHeaders = new ConcurrentHashMap<>();
		amqHeaders.put("retryCounter", retryCounter);
		amqHeaders.put("receivedTime", receivedTime);
		amqHeaders.put("requestId", requestId);
		amqHeaders.put("processRequestId", processRequestId);
		amqHeaders.put("retryStatusCode", retryStatusCode);
		amqHeaders.put("clientCode", clientCode);
		amqHeaders.put("clientKey", clientKey);
		amqHeaders.put("apiVersion", String.valueOf(apiVersion));

		String eventId = processRequestId;
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		String requestTime = utils.getLocalDateTime(new Date()).format(dateTimeFormatter);
		Logger loggerInvoiceSubmission = LogManager.getLogger("InvoiceSubmission");
		String remark = "";
	  	String apiUrl = "";
	  	InvoiceSubmissionApiReqBodyDto invoiceRequestBody = null;
	  	routingKey = routingKey.contains(".retry") ? routingKey : routingKey + ".retry";
	  	
	  	try {
	  		
	  		String message = new String(data, "UTF-8");
			
			// Map string to object class
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			invoiceRequestBody = objectMapper.readValue(message, InvoiceSubmissionApiReqBodyDto.class);
			
			CommunicationApiDetailsDto commApi = null;
			commApi = communicationApiService.findByClientIdClientKeyAndApiCategoryIdAndApiVersionNumber(Constants.IRCS_CORE_CODE
					,clientKey,Constants.API_CATEG_INVOICE_SUBMISSION,apiVersion);
		
			if (commApi != null) {
				
				if (commApi.getApiStatusId().equals(Constants.RECORD_STATUS_ACTIVE)) {
					
					if (commApi.getSystemStatusId().equals(Constants.RECORD_STATUS_ACTIVE)) {
						
						if (commApi.getOutboundStatusId().equals(Constants.RECORD_STATUS_ACTIVE)) {
							
							String certificateFile = pkiUtils.appClientKeyFilePath + commApi.getCertificateFilename();
							String certificatePassPhrase = commApi.getCertificatePassphrase();
							String certificateAlias = commApi.getCertificateAlias();
							Long sigAlg = commApi.getSignatureAlgo();
							apiUrl = commApi.getApiUrl();
							
							if (utils.isFileExist(certificateFile) && !utils.isNullOrEmpty(certificatePassPhrase) && !utils.isNullOrEmpty(certificateAlias)){
								
								String invoiceRequest = utils.generateXmlString(JAXBContext.newInstance(InvoiceSubmissionApiReqBodyDto.class),invoiceRequestBody);
			    				invoiceRequest = utils.getStringWithinXml(invoiceRequest, "invoice");
			    				String signature = pkiUtils.createSignature(invoiceRequest,commApi.getInternalCertificatePassphrase(),commApi.getInternalCertificateAlias(),
			    						 pkiUtils.appPrivateKeyFilePath+commApi.getInternalCertificateFilename(),Constants.SIG_ALG_SHA2);
			    				
			    				InvoiceSubmissionApiReqDto invoiceApiReq = new InvoiceSubmissionApiReqDto(invoiceRequestBody,signature);
			    				String signedInvoiceRequest = utils.generateXmlString(JAXBContext.newInstance(InvoiceSubmissionApiReqDto.class),invoiceApiReq);
								
			    				loggerInvoiceSubmission.info("\nEvent : Invoice Submission , EventId :"+eventId+",RequestId :"+requestId+",RequestTime :"+requestTime+""
										+ "\nServiceInstitution: "+clientCode+",ClientKey :"+clientKey+",At Url :"+apiUrl+",AttemptCount :"+retryCounter+",\nContent :"+signedInvoiceRequest);
								
								//Send Message
								Response receivedAcknowledgement = null;
								Hashtable<String, String> requestHeaders = new Hashtable<>();
								requestHeaders.put("clientcode", clientCode);
								requestHeaders.put("clientkey", Constants.PORTAL_CLIENT_KEY);
								
								if(Integer.parseInt(retryCounter) == 0) {
									receivedAcknowledgement = utils.sendMsgToExternalSystem(apiUrl,signedInvoiceRequest, "XML", "POST",requestHeaders);
								}else {
									receivedAcknowledgement = utils.resendMsgToExternalSystem(apiUrl,signedInvoiceRequest, "XML", "POST",requestHeaders);
								}
								
								String acknowledgementTime = LocalDateTime.now().format(dateTimeFormatter);
								
								Integer statusCode = Integer.valueOf(receivedAcknowledgement.getStatus());
								String acknowledgementData = String.valueOf(receivedAcknowledgement.getData());
								if (statusCode == 100 || (statusCode >= 200 && statusCode < 299)) {
									
									if (!utils.isNullOrEmpty(acknowledgementData)) {//Process Returned Content
										
										try {//Verify returned content
											
											JAXBContext jaxbContext = JAXBContext.newInstance(InvoiceSubmissionApiAckDto.class);
											Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
											StringReader stringReader = new StringReader(acknowledgementData);

											InvoiceSubmissionApiAckDto invoiceSubmissionApiAck = (InvoiceSubmissionApiAckDto) unmarshaller.unmarshal(stringReader);
											InvoiceSubmissionApiAckBodyDto invoiceSubmissionApiAckBody = invoiceSubmissionApiAck.getMessageBody();
											String acknowledgementMessage = utils.getStringWithinXml(acknowledgementData,"invoice");
											String acknowledgementSignature = invoiceSubmissionApiAck.getMessageHash();

											//Check Content Returned
											if(!StringUtils.isBlank(acknowledgementMessage) && !StringUtils.isBlank(acknowledgementSignature)) {
											
												//Verify Signature
										         if(pkiUtils.verifySignature(acknowledgementSignature, acknowledgementMessage,certificatePassPhrase,certificateAlias,certificateFile,sigAlg)){
										        	 
										        	 if (invoiceSubmissionApiAckBody.getAcknowledgementStatus().equals("1010")) {
										        		 
										        		 remark = "Message delivered successfully";
										        		 loggerInvoiceSubmission.info("\nEvent : Invoice Submission , EventId :"+eventId+",RequestId :"+requestId+",RequestTime :"+requestTime+","
										        		 		+ " AcknowledgementTime "+acknowledgementTime+" ,\nServiceInstitution: "+clientCode+",ClientKey :"+clientKey+",At Url :"+apiUrl+""
										        		 				+ ",AttemptCount :"+retryCounter+","+ " StatusCode : "+statusCode+", Remark :"+remark+"\nReceivedContent:"+acknowledgementData+"\n");
										        		 
										        	 } else { 
										        		 //Failure
										        		 remark = "Message delivery failed";
										        		 loggerInvoiceSubmission.error("\nEvent : Invoice Submission , EventId :"+eventId+",RequestId :"+requestId+",RequestTime :"+requestTime+""
										        				 + " AcknowledgementTime "+acknowledgementTime+" ,\nServiceInstitution: "+clientCode+",ClientKey :"+clientKey+",At Url :"+apiUrl+""
										        				 		+ ",AttemptCount :"+retryCounter+", StatusCode : "+statusCode+", Remark :"+remark+"\nReceivedContent:"+acknowledgementData+"\n");
										        		 utils.publishMsgToRetryExchange(this.messageOutRetryExchange, routingKey,invoiceRequestBody, amqHeaders);
										        	 }
										         } else {
										        	 //Invalid Signature
										        	 remark = "Invalid acknowledgement signature";
										        	 loggerInvoiceSubmission.error("\nEvent : Invoice Submission , EventId :"+eventId+",RequestId :"+requestId+",RequestTime :"+requestTime+""
										        			 + " AcknowledgementTime "+acknowledgementTime+ "\nServiceInstitution: "+clientCode+",ClientKey :"+clientKey+",At Url :"+apiUrl+""
										        			 		+ ",AttemptCount :"+retryCounter+", StatusCode : "+statusCode+", Remark :"+remark+"\nReceivedContent:"+acknowledgementData+"\n");
										        	 utils.publishMsgToRetryExchange(this.messageOutRetryExchange, routingKey,invoiceRequestBody, amqHeaders);
										         }
											} else {
												// Invalid Content
												remark = "Empty or Invalid Content Returned";
												loggerInvoiceSubmission.error("\nEvent : Invoice Submission , EventId :"+eventId+",RequestId :"+requestId+",RequestTime :"+requestTime+""
														+ " AcknowledgementTime "+acknowledgementTime+ ",\nServiceInstitution: "+clientCode+",ClientKey :"+clientKey+",At Url :"+apiUrl+""
																+ ",AttemptCount :"+retryCounter+",StatusCode : "+statusCode+", Remark :"+remark+"\nReceivedContent:"+acknowledgementData+"\n");
												utils.publishMsgToRetryExchange(this.messageOutRetryExchange, routingKey,invoiceRequestBody, amqHeaders);
											}
										} catch (Exception e) {
											//An Error Occurred
											Map<String,String> stackDetails = utils.getExceptionStackDetails(e);
											remark = "An error occured while processing acknowledgement. Empty or Invalid Content Returned."+stackDetails;
											loggerInvoiceSubmission.error("\nEvent : Invoice Submission , EventId :"+eventId+",RequestId :"+requestId+",RequestTime :"+requestTime+""
													+ " AcknowledgementTime "+acknowledgementTime+ ",\nServiceInstitution: "+clientCode+",ClientKey :"+clientKey+",At Url :"+apiUrl+""
															+ ",AttemptCount :"+retryCounter+", StatusCode : "+statusCode+", Remark :"+remark+"\nReceivedContent:"+acknowledgementData+"\n");
											utils.publishMsgToRetryExchange(this.messageOutRetryExchange, routingKey,invoiceRequestBody, amqHeaders);
										}
									} else {
										//Invalid Content
										remark = "Empty or Invalid Content Returned.";
										loggerInvoiceSubmission.error("\nEvent : Invoice Submission , EventId :"+eventId+",RequestId :"+requestId+",RequestTime :"+requestTime+""
												+ "\nServiceInstitution: "+clientCode+",ClientKey :"+clientKey+",At Url :"+apiUrl+",AttemptCount :"+retryCounter+","
														+ " StatusCode : "+statusCode+", Remark :"+remark+"\nReceivedContent:"+acknowledgementData+"\n");
										utils.publishMsgToRetryExchange(this.messageOutRetryExchange, routingKey,invoiceRequestBody, amqHeaders);
									}
								} else {
									remark = receivedAcknowledgement.getMessage();
									loggerInvoiceSubmission.error("\nEvent : Invoice Submission , EventId :"+eventId+",RequestId :"+requestId+",RequestTime :"+requestTime+""
											+ "\nServiceInstitution: "+clientCode+",ClientKey :"+clientKey+",At Url :"+apiUrl+",AttemptCount :"+retryCounter+","
													+ " StatusCode : "+statusCode+", Remark :"+remark+"\nReceivedContent:"+acknowledgementData+"\n");
									utils.publishMsgToRetryExchange(this.messageOutRetryExchange, routingKey,invoiceRequestBody, amqHeaders);
									
								}
							} else {
								//Invalid Client PKI Configurations
								remark = "Invalid Client Signature Configuration one of File/Passphrase/Alias does not exist.";
								loggerInvoiceSubmission.fatal("\nEvent : Invoice Submission , EventId :"+eventId+",RequestId :"+requestId+",RequestTime :"+requestTime+""
										+ "\nServiceInstitution: "+clientCode+",ClientKey :"+clientKey+",At Url :"+apiUrl+",AttemptCount :"+retryCounter+",\nRemark :"+remark);
								
								utils.publishMsgToRetryExchange(this.messageOutRetryExchange, routingKey,invoiceRequestBody, amqHeaders);
							}
						} else {
							//Client system not allowed to receive communications
							remark = "Client system not allowed to receive communications";
							loggerInvoiceSubmission.error("\nEvent : Invoice Submission , EventId :"+eventId+",RequestId :"+requestId+",RequestTime :"+requestTime+""
									+ "\nServiceInstitution: "+clientCode+",ClientKey :"+clientKey+",At Url :"+apiUrl+",AttemptCount :"+retryCounter+",\nRemark :"+remark);
							
							utils.publishMsgToRetryExchange(this.messageOutRetryExchange, routingKey,invoiceRequestBody, amqHeaders);
						}
					} else {
						//Inactive Client System Configuration
						remark = "Inactive or invalid client system details configuration";
						loggerInvoiceSubmission.error("\nEvent : Invoice Submission , EventId :"+eventId+",RequestId :"+requestId+",RequestTime :"+requestTime+""
								+ "\nServiceInstitution: "+clientCode+",ClientKey :"+clientKey+",At Url :"+apiUrl+",AttemptCount :"+retryCounter+",\nRemark :"+remark);
						
						utils.publishMsgToRetryExchange(this.messageOutRetryExchange, routingKey,invoiceRequestBody, amqHeaders);
					}
				} else {
					//Inactive configuration for invoice submission"
					remark = "Inactive configuration for invoice submission";
					loggerInvoiceSubmission.error("\nEvent : Invoice Submission , EventId :"+eventId+",RequestId :"+requestId+",RequestTime :"+requestTime+""
							+ "\nServiceInstitution: "+clientCode+",ClientKey :"+clientKey+",At Url :"+apiUrl+",AttemptCount :"+retryCounter+",\nRemark :"+remark);
					
					utils.publishMsgToRetryExchange(this.messageOutRetryExchange, routingKey,invoiceRequestBody, amqHeaders);
				}
			} else {
				//No configurations found
				remark = "Configuration for invoice submission not found";
				loggerInvoiceSubmission.fatal("\nEvent : Invoice Submission , EventId :"+eventId+",RequestId :"+requestId+",RequestTime :"+requestTime+""
						+ "\nServiceInstitution: "+clientCode+",ClientKey :"+clientKey+",At Url :"+apiUrl+",AttemptCount :"+retryCounter+",\nRemark :"+remark);
				
				utils.publishMsgToRetryExchange(this.messageOutRetryExchange, routingKey,invoiceRequestBody, amqHeaders);
			}
	  	} catch(Exception ex) {
	  	//An Error Occurred
			Map<String,String> stackDetails = utils.getExceptionStackDetails(ex);
			remark = "An error occured while processing Invoice Submission."+stackDetails;
			loggerInvoiceSubmission.fatal("\nEvent : Invoice Submission , EventId :"+eventId+",RequestId :"+requestId+",RequestTime :"+requestTime+""
					+ "\nServiceInstitution: "+clientCode+",ClientKey :"+clientKey+",At Url :"+apiUrl+",AttemptCount :"+retryCounter+",\nRemark :"+remark);
			
			utils.publishMsgToRetryExchange(this.messageOutRetryExchange, routingKey, invoiceRequestBody, amqHeaders);
		}
	}

	/**
	 * Save Invoice Request Details
	 * @param invoiceRequestBody
	 * @param amqHeaders
	 * @param requestTime
	 */
	public Boolean saveInvoiceRequestDetails(InvoiceSubmissionApiReqBodyDto invoiceRequestBody,
			Map<String, String> amqHeaders, String requestTime) {
		
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		BigDecimal paidPmount = new BigDecimal("0.0");
		Boolean isInvoicePaid = false;
		Date nextReminder = null;
		String paymentNumber = null;
		Date processEndTime = null;
		String clientCode = amqHeaders.get("clientCode");
		String requestId = amqHeaders.get("requestId");
		String receivedTime = amqHeaders.get("receivedTime");
		Boolean saveStatus = false;
		
		try {
			
			InvoiceSubmissionApiReqHeaderDto invoiceHeader =  invoiceRequestBody.getInvoiceHeader();
			InvoiceSubmissionApiReqDetailDto invoiceReqDetail = invoiceRequestBody.getInvoiceDetails()
					.getInvoiceDetailList().get(0);
			List<InvoiceSubmissionApiReqServiceDto> invoiceServices = invoiceReqDetail.getInvoiceServices().getInvoiceServiceDetailList();
			ServiceDepartment serviceDepartment = invoiceServices.get(0).getRevenueSource().getServiceDepartment();
			WorkStation workstation = workStationService.findByWorkStationCode(invoiceReqDetail.getWorkStation());
			
			Invoice invoice = new Invoice(
					invoiceReqDetail.getInvoiceNumber(),
					invoiceHeader.getInvoiceType(),
					invoiceReqDetail.getInvoiceDescription(),
					paymentNumber,
					paymentNumber,
					String.valueOf(invoiceReqDetail.getInvoiceReference()),
					invoiceReqDetail.getApplicationNumber(),
					invoiceReqDetail.getInvoiceReferencePath(),
					invoiceReqDetail.getCustomerName(),
					invoiceReqDetail.getCustomerIdentity(),
					invoiceReqDetail.getCustomerIdentityType(),
					invoiceReqDetail.getCustomerPhoneNumber(),
					invoiceReqDetail.getCustomerEmail(),
					invoiceReqDetail.getCustomerAccount(),
					java.util.Date.from(LocalDateTime.parse(invoiceReqDetail.getInvoiceIssueDate(), dateTimeFormatter)
							.atZone(ZoneId.systemDefault()).toInstant()),
					java.util.Date.from(LocalDateTime.parse(invoiceReqDetail.getInvoiceExpiryDate(), dateTimeFormatter)
							.atZone(ZoneId.systemDefault()).toInstant()),
					invoiceReqDetail.getIssuedBy(),
					invoiceReqDetail.getApprovedBy(),
					invoiceReqDetail.getPaymentOption(),
					Constants.PAY_OPT_PRECISE,
					invoiceReqDetail.getInvoiceAmount(),
					invoiceReqDetail.getMinPaymentAmount(),
					paidPmount,
					invoiceReqDetail.getCurrency(),
					invoiceReqDetail.getExchangeRate(),
					isInvoicePaid,
					invoiceReqDetail.getInvoicePayPlan(),
					Constants.PAY_PLAN_POSTPAID,
					nextReminder,
					Constants.REMINDER_STATUS_OFF,
					invoiceHeader.getDetailCount(),
					serviceDepartment.getServiceInstitution().getInstitutionCode(),
					serviceDepartment.getServiceInstitution(),
					workstation.getWorkStationCode(),
					workstation,
					clientCode,
					requestId,
					java.util.Date.from(LocalDateTime.parse(receivedTime, dateTimeFormatter)
							.atZone(ZoneId.systemDefault()).toInstant()),
					java.util.Date.from(LocalDateTime.parse(requestTime, dateTimeFormatter)
							.atZone(ZoneId.systemDefault()).toInstant()),
					processEndTime,
					invoiceReqDetail.getIssuedbyUserId(),
					invoiceReqDetail.getIssuedBy()
					);
			
			Invoice newInvoice = invoiceService.saveInvoice(invoice);
			if(newInvoice != null) {
				
				List<InvoiceItem> invoiceItemList = new ArrayList<>();
				for(InvoiceSubmissionApiReqServiceDto itemDetail : invoiceServices) {
					
					InvoiceItem item = new InvoiceItem(itemDetail.getServiceDepartment(),itemDetail.getServiceType(),
							itemDetail.getRevenueSource().getServiceType().getServiceTypeName(),itemDetail.getRevenueSource(),
							itemDetail.getServiceReference(),itemDetail.getServiceAmount(),newInvoice.getId(),itemDetail.getPaymentPriority());
					invoiceItemList.add(item);
				}
				invoiceItemService.saveInvoiceItemList(invoiceItemList);
				
				saveStatus = true;
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return saveStatus;
	}
	

}

