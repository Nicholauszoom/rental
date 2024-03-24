package com.dflex.ircs.portal.invoice.api.controller;

import java.io.StringReader;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dflex.ircs.portal.application.entity.ApplicationWorkFlow;
import com.dflex.ircs.portal.application.service.ApplicationWorkFlowService;
import com.dflex.ircs.portal.auth.dto.CommunicationApiDetailsDto;
import com.dflex.ircs.portal.auth.service.ClientSystemHostService;
import com.dflex.ircs.portal.auth.service.CommunicationApiService;
import com.dflex.ircs.portal.data.entity.FormData1;
import com.dflex.ircs.portal.data.service.FormDataService;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceSubmissionApiAckBodyDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceSubmissionApiAckDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceSubmissionApiResBodyDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceSubmissionApiResDetailDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceSubmissionApiResDto;
import com.dflex.ircs.portal.invoice.entity.Invoice;
import com.dflex.ircs.portal.invoice.service.InvoiceService;
import com.dflex.ircs.portal.util.Constants;
import com.dflex.ircs.portal.util.PKIUtils;
import com.dflex.ircs.portal.util.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

/**
 * 
 * @author Augustino Mwageni
 * 
 * Receive Invoice Response
 *
 */
@RestController
@RequestMapping("/api/invoice")
public class InvoiceNotificationApiController {
	
	@Autowired
	private ApplicationWorkFlowService applicationWorkFlowService;
	
	@Autowired
	private FormDataService formDataService;
	
	@Autowired
	private CommunicationApiService communicationApiService;
	
	@Autowired
	private ClientSystemHostService clientSystemHostService;
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Value("${rabbitmq.ircs.incoming.exchange}")
	private String messageInExchange;
	
	@Value("${rabbitmq.routingkey.prefix}")
	private String routingKeyPrefix;
	
	@Value("${rabbitmq.ircs.si.invoice.incoming.routingkey}")
	private String invoiceInRoutingKey;
	
	@Value("${app.validate.client.system.host}")
	private Boolean validateRequestHost;
	
	@Autowired
	private Utils utils;
	
	@Autowired
	private PKIUtils pkiUtils;
	
	@Autowired
	private MessageSource messageSource;
	
	@PostMapping("/submit/response-v1")
	public String receiveInvoiceResponse(@RequestBody String requestContent, @RequestHeader Map<String, String> requestHeaders
			,HttpServletRequest request) {
		
		Locale currentLocale = LocaleContextHolder.getLocale();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		String receivedTime = utils.getLocalDateTime(new Date()).format(dateTimeFormatter);
		String processRequestId = utils.getToken();
		String acknowledgementStatusCode = "";
		String acknowledgementStatusDesc = "";
		String remark = "";
		String url = "";
		String clientKey = "";
		Integer logLevel = 0;
		Long apiVersionNumber = 1L;
		String clientCode = "";
		
		String requestId = utils.getStringWithinXmlTagExclusive(requestContent, "reqid");
		String requestIp = utils.getRequestIP(request);
		String requestProtocol = request.getProtocol();
		CommunicationApiDetailsDto commApi = null;
		InvoiceSubmissionApiAckBodyDto invoiceSubmissionApiAck = null;
		url = request.getRequestURI();
		Logger loggerInvoiceNotification= LogManager.getLogger("InvoiceNotification");
		loggerInvoiceNotification.info("Event : Invoice Notification,EventId :"+processRequestId+",RequestId :"+requestId+""
				+ ",ReceivedTime :"+receivedTime+"\nReceivedHeaders : "+requestHeaders+",\nAt Url :"+url+" ,RequestIp :"+requestIp+","
						+ " RequestProtocol :"+requestProtocol+", \nReceivedContent :"+requestContent+"\n");
		
		try {
			
			if (requestHeaders.containsKey("clientkey") && requestHeaders.containsKey("clientcode")) {
				
				if(!utils.isNullOrEmpty(requestHeaders.get("clientcode")) && !utils.isNullOrEmpty(requestHeaders.get("clientkey"))) {
					
					clientKey = requestHeaders.get("clientkey");
					clientCode = requestHeaders.get("clientcode");
					
					Boolean validClientSystemHost = clientSystemHostService.existsByClientKeyAndRecordStatusIdAndIpAddress(clientKey,
							Constants.RECORD_STATUS_ACTIVE, requestIp);
					if((validClientSystemHost && validateRequestHost) || !validateRequestHost) {
						
						// Validate Incoming Request Schema
//						InputStream requestXsdSchema = getClass().getResourceAsStream("/xsd-templates/InvoiceNotificationV1.xsd");
//						Map<String,String> schemaValidationResult = utils.validateRequestXMLSchema(requestXsdSchema, requestContent);
//						if (schemaValidationResult.get("code").equals("1001")) {
							
							// Read xml 
							JAXBContext jaxbContext = JAXBContext.newInstance(InvoiceSubmissionApiResDto.class);
							Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
							StringReader stringReader = new StringReader(requestContent);
							InvoiceSubmissionApiResDto invoiceSubmissionApiRes = (InvoiceSubmissionApiResDto) unmarshaller.unmarshal(stringReader);
							
							String requestMessage = utils.getStringWithinXml(requestContent, "invoice");
							String requestSignature = invoiceSubmissionApiRes.getMessageHash();
							InvoiceSubmissionApiResBodyDto invoiceSubmissionApiResBody = invoiceSubmissionApiRes.getMessageBody();
							
							try {
								
								commApi = communicationApiService.findByClientIdClientKeyAndApiCategoryIdAndApiVersionNumber(clientCode,clientKey,
										Constants.API_CATEG_INVOICE_NOTIFICATION,apiVersionNumber);
								
								if(commApi != null) {
									
									if(commApi.getApiStatusId().equals(Constants.RECORD_STATUS_ACTIVE)) {
										
										if(commApi.getInboundStatusId().equals(Constants.RECORD_STATUS_ACTIVE)) {
											
											if(commApi.getSystemClientStatusId() != null && commApi.getSystemClientStatusId().equals(Constants.RECORD_STATUS_ACTIVE)) {
												
												if(commApi.getCertificateStatusId().equals(Constants.RECORD_STATUS_ACTIVE)) {
													
													String certificateFile = pkiUtils.appClientKeyFilePath + commApi.getCertificateFilename();
													String certificatePassPhrase = commApi.getCertificatePassphrase();
													String certificateAlias = commApi.getCertificateAlias();
													Long sigAlg = commApi.getSignatureAlgo();
													Date today = new Date();
													
													if (utils.isFileExist(certificateFile)){
														
														if(!utils.isNullOrEmpty(certificatePassPhrase) && !utils.isNullOrEmpty(certificateAlias)) {
															
															if (pkiUtils.verifySignature(requestSignature, requestMessage,certificatePassPhrase, certificateAlias, certificateFile,sigAlg)) {
																
																System.out.println("Update details******************************************");
																List<InvoiceSubmissionApiResDetailDto> invoiceDetailList = invoiceSubmissionApiResBody.getInvoiceDetail().getInvoiceDetailList();
																Invoice invoice = invoiceService.findByInvoiceNumber(invoiceDetailList.get(0).getInvoiceNumber());
																if(invoice != null) {
																	
																	invoice.setPaymentNumber(invoiceDetailList.get(0).getInvoicePaymentNumber());
																	invoice.setInvoicePaymentNumber(invoiceDetailList.get(0).getInvoicePaymentNumber());
																	invoice.setProcessEndDate(today);
																	
																	Invoice updatedInvoice = invoiceService.saveInvoice(invoice);
																	Optional<FormData1> application = formDataService.findFormData1ById(Long.parseLong(updatedInvoice.getReference()));
													    			if(application.isPresent()) {
													    				
													    				application.get().setWorkFlowName(Constants.WORK_FLOW_PAYMENT);
													    				application.get().setWorkFlowId(Constants.WORK_FLOW_PAYMENT_ID);
													    				application.get().setWorkFlowActionName(Constants.ACTION_BILLING_COMPLETED);
													    				application.get().setWorkFlowActionId(Constants.ACTION_BILLING_COMPLETED_ID);
													    				application.get().setUpdatedBy(Constants.DEFAULT_SYS_USERID);
													    				application.get().setUpdatedByUserName(Constants.DEFAULT_SYS_USERNAME);
													    				application.get().setUpdatedDate(today);
													    				application.get().setWorkFlowRemark(Constants.WORK_FLOW_PAYMENT);
													    				
													    				FormData1 newFormData = formDataService.saveFormData1(application.get());
													    				if(newFormData != null) {
													    					
													    					/**
													            			 * Create Workflow
													            			 */
													            			ApplicationWorkFlow workFlow = new ApplicationWorkFlow(Constants.DEFAULT_SYS_USERID,Constants.DEFAULT_SYS_USERNAME,newFormData.getId(),
													            					newFormData.getApplicationUid(),newFormData.getWorkFlowId(),newFormData.getWorkFlowName(),newFormData.getWorkFlowActionId(),
													            					newFormData.getWorkFlowActionName(),newFormData.getWorkFlowRemark());
													            			
													            			applicationWorkFlowService.saveApplicationWorkFlow(workFlow);
													    				}
													    			}
																}
																acknowledgementStatusCode = messageSource.getMessage("code.1010", null, currentLocale);
																acknowledgementStatusDesc = messageSource.getMessage("message.1010", null, currentLocale);
																logLevel = 2;
																remark = "Successful";
																invoiceSubmissionApiAck = new InvoiceSubmissionApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
																		,acknowledgementStatusDesc);
															} else {
																logLevel = 4;
																acknowledgementStatusCode = messageSource.getMessage("code.1016", null, currentLocale);
																acknowledgementStatusDesc = messageSource.getMessage("message.1016", null, currentLocale);
																remark = "Invalid message signature";
																invoiceSubmissionApiAck = new InvoiceSubmissionApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
																		,acknowledgementStatusDesc);
															}
														} else {
															logLevel = 5;
															acknowledgementStatusCode = messageSource.getMessage("code.1015", null, currentLocale);
															acknowledgementStatusDesc = messageSource.getMessage("message.1015", null, currentLocale);
															remark = "Bad Client PKI configurations";
															invoiceSubmissionApiAck = new InvoiceSubmissionApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
																	,acknowledgementStatusDesc);
														}
													} else {
														logLevel = 5;
														acknowledgementStatusCode = messageSource.getMessage("code.1014", null, currentLocale);
														acknowledgementStatusDesc = messageSource.getMessage("message.1014", null, currentLocale);
														remark = "Client PKI Certificate File is Missing or configured wrongly";
														invoiceSubmissionApiAck = new InvoiceSubmissionApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
																,acknowledgementStatusDesc);
													}
												} else {
													logLevel = 4;
													acknowledgementStatusCode = messageSource.getMessage("code.1040", null, currentLocale);
													acknowledgementStatusDesc = messageSource.getMessage("message.1040", null, currentLocale);
													remark = "Inactive or invalid client system pki certificate";
													invoiceSubmissionApiAck = new InvoiceSubmissionApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
															,acknowledgementStatusDesc);
												}
											} else {
												logLevel = 4;
												acknowledgementStatusCode = messageSource.getMessage("code.1039", null, currentLocale);
												acknowledgementStatusDesc = messageSource.getMessage("message.1039", null, currentLocale);
												remark = "Client is prohibited from utilizing the request system.";
												invoiceSubmissionApiAck = new InvoiceSubmissionApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
														,acknowledgementStatusDesc);
											}
										} else {
											logLevel = 4;
											acknowledgementStatusCode = messageSource.getMessage("code.1041", null, currentLocale);
											acknowledgementStatusDesc = messageSource.getMessage("message.1041", null, currentLocale);
											remark = "Client inbound communication not allowed";
											invoiceSubmissionApiAck = new InvoiceSubmissionApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
													,acknowledgementStatusDesc);
										}
									} else {
										logLevel = 4;
										acknowledgementStatusCode = messageSource.getMessage("code.1012", null, currentLocale);
										acknowledgementStatusDesc = messageSource.getMessage("message.1012", null, currentLocale);
										remark = "Inactive client configuration";
										invoiceSubmissionApiAck = new InvoiceSubmissionApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
												,acknowledgementStatusDesc);
									}
								} else {
									logLevel = 5;
									acknowledgementStatusCode = messageSource.getMessage("code.1011", null, currentLocale);
									acknowledgementStatusDesc = messageSource.getMessage("message.1011", null, currentLocale);
									remark = "No client configuration found";
									invoiceSubmissionApiAck = new InvoiceSubmissionApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
											,acknowledgementStatusDesc);
								}
							} catch(Exception ex) {
								ex.printStackTrace();
								logLevel = 5;
								acknowledgementStatusCode = messageSource.getMessage("code.1006", null, currentLocale);
								acknowledgementStatusDesc = messageSource.getMessage("message.1006", null, currentLocale);
								remark = "An error occured while processing request,"+ex.getMessage();
								invoiceSubmissionApiAck = new InvoiceSubmissionApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
										,acknowledgementStatusDesc);
							}
//						} else {
//							
//							logLevel = 4;
//							acknowledgementStatusCode = schemaValidationResult.get("code");
//							acknowledgementStatusDesc = schemaValidationResult.get("remark");
//							remark = "Invalid request data, "+schemaValidationResult.get("message");
//							invoiceSubmissionApiAck = new InvoiceSubmissionApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
//									,acknowledgementStatusDesc);
//						}
					} else {
						logLevel = 5;
						acknowledgementStatusCode = messageSource.getMessage("code.1038", null, currentLocale);
						acknowledgementStatusDesc = messageSource.getMessage("message.1038", null, currentLocale);
						remark = "Invalid client system host";
						invoiceSubmissionApiAck = new InvoiceSubmissionApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
								,acknowledgementStatusDesc);
					}
				} else {
					
					logLevel = 5;
					acknowledgementStatusCode = messageSource.getMessage("code.1008", null, currentLocale);
					acknowledgementStatusDesc = messageSource.getMessage("message.1008", null, currentLocale);
					remark = "Missing or invalid request header";
					invoiceSubmissionApiAck = new InvoiceSubmissionApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
							,acknowledgementStatusDesc);
				}
			} else {
				
				logLevel = 4;
				acknowledgementStatusCode = messageSource.getMessage("code.1008", null, currentLocale);
				acknowledgementStatusDesc = messageSource.getMessage("message.1008", null, currentLocale);
				remark = "Missing or invalid request header";
				invoiceSubmissionApiAck = new InvoiceSubmissionApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
						,acknowledgementStatusDesc);
			}
		} catch(Exception e) {
			e.printStackTrace();
			logLevel = 4;
			acknowledgementStatusCode = messageSource.getMessage("code.1002", null, currentLocale);
			acknowledgementStatusDesc = messageSource.getMessage("message.1002", null, currentLocale);
			remark = "An error occured while request processing, "+e.getMessage();
			invoiceSubmissionApiAck = new InvoiceSubmissionApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
					,acknowledgementStatusDesc);
		}
		return prepareInvoiceNotificationApiAck(invoiceSubmissionApiAck,currentLocale,commApi,processRequestId,requestId,remark,url,clientCode,clientKey,logLevel);
	}
		
	
	/**
	 * Prepare invoice notification api response acknowledgement
	 * @param invoiceSubmissionApiAck
	 * @param currentLocale
	 * @param commApi 
	 * @param applySecondaryKey
	 * @param processRequestId
	 * @param requestId
	 * @param remark
	 * @param url
	 * @param clientCode
	 * @param logLevel
	 * @return String
	 */
	private String prepareInvoiceNotificationApiAck(InvoiceSubmissionApiAckBodyDto invoiceSubmissionApiAck,
			Locale currentLocale, CommunicationApiDetailsDto commApi, String processRequestId, String requestId, String remark,
			String url, String clientCode, String clientKey, Integer logLevel) {
		
		String signedResponse = "";
		String signature = "";
		String requestResponse = "";
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		String acknowledgeTime = utils.getLocalDateTime(new Date()).format(dateTimeFormatter);
		Logger loggerInvoiceSubmission= LogManager.getLogger("InvoiceSubmission");
		
		try {
			
			requestResponse = utils.generateXmlString(JAXBContext.newInstance(InvoiceSubmissionApiAckBodyDto.class),invoiceSubmissionApiAck);
			requestResponse = utils.getStringWithinXml(requestResponse, "invoice");
			if(commApi != null ){
				String appKeyFileName = commApi.getInternalCertificateFilename();
				String appKeyAlias = commApi.getInternalCertificateAlias();
				String appKeyPassphrase = commApi.getInternalCertificatePassphrase();
				String appKeyFile = pkiUtils.appClientKeyFilePath + appKeyFileName;
				Long sigAlg = commApi.getSignatureAlgo();
				
				signature = pkiUtils.createSignature(requestResponse,appKeyPassphrase,appKeyAlias,appKeyFile,sigAlg);
			} else {
				signature = pkiUtils.createSignature(requestResponse,pkiUtils.appPassphrase,pkiUtils.appAlias,pkiUtils.appKeyFile,Constants.SIG_ALG_SHA2);
			}
			
			InvoiceSubmissionApiAckDto submissionApiAck = new InvoiceSubmissionApiAckDto(invoiceSubmissionApiAck,signature);
			signedResponse = utils.generateXmlString(JAXBContext.newInstance(InvoiceSubmissionApiAckDto.class),submissionApiAck);
	
			if(logLevel == Constants.LOG_ERROR) {
				//log error 
				loggerInvoiceSubmission.error("\nEvent : Invoice Notifiation Api Acknowledgement,EventId :"+processRequestId+",RequestId :"+requestId+",AcknowledgementTime :"+acknowledgeTime+""
						+ "\nClient : "+clientCode+",ClientKey :"+clientKey+",At Url :"+url+"\nRemark :"+remark+",\nAcknowledgementContent :"+signedResponse);
			}else if(logLevel == Constants.LOG_FATAL) {
				//log fatal  
				loggerInvoiceSubmission.fatal("\nEvent : Invoice Notifiation Api Acknowledgement,EventId :"+processRequestId+",RequestId :"+requestId+",AcknowledgementTime :"+acknowledgeTime+""
						+ "\nClient : "+clientCode+",ClientKey :"+clientKey+",At Url :"+url+"\nRemark :"+remark+",\nAcknowledgementContent :"+signedResponse);
			}else {
				//log info
				loggerInvoiceSubmission.info("\nEvent : Invoice Notifiation Api Acknowledgement,EventId :"+processRequestId+",RequestId :"+requestId+",AcknowledgementTime :"+acknowledgeTime+""
						+ "\nClient : "+clientCode+",ClientKey :"+clientKey+",At Url :"+url+"\nRemark :"+remark+",\nAcknowledgementContent :"+signedResponse);
			}
		}catch(Exception ex) {
			//log error
			remark = ex.getMessage();
			loggerInvoiceSubmission.error("\nEvent : Invoice Notifiation Api Acknowledgement,EventId :"+processRequestId+",RequestId :"+requestId+",AcknowledgementTime :"+acknowledgeTime+""
					+ "\nClient : "+clientCode+",ClientKey :"+clientKey+",At Url :"+url+"\nRemark :"+remark+",\nAcknowledgementContent :"+signedResponse);
			ex.printStackTrace();
		}
		
		return signedResponse;
	}
	

}
