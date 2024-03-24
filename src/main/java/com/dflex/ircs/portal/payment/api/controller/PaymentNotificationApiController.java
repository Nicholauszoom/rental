package com.dflex.ircs.portal.payment.api.controller;

import java.io.StringReader;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

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
import com.dflex.ircs.portal.invoice.entity.Invoice;
import com.dflex.ircs.portal.invoice.service.InvoiceService;
import com.dflex.ircs.portal.payment.api.dto.PaymentNotificationApiAckBodyDto;
import com.dflex.ircs.portal.payment.api.dto.PaymentNotificationApiAckDto;
import com.dflex.ircs.portal.payment.api.dto.PaymentNotificationApiReqBodyDto;
import com.dflex.ircs.portal.payment.api.dto.PaymentNotificationApiReqDetailDto;
import com.dflex.ircs.portal.payment.api.dto.PaymentNotificationApiReqDetailsDto;
import com.dflex.ircs.portal.payment.api.dto.PaymentNotificationApiReqDto;
import com.dflex.ircs.portal.payment.api.dto.PaymentNotificationApiReqHeaderDto;
import com.dflex.ircs.portal.setup.entity.CollectionAccount;
import com.dflex.ircs.portal.setup.service.CollectionAccountService;
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
 * Receive Invoice Payment Notification Requests
 *
 */
@RestController
@RequestMapping("/api/payment")
public class PaymentNotificationApiController {
	
	@Autowired
	private ClientSystemHostService clientSystemHostService;
	
	@Autowired
	private CommunicationApiService communicationApiService;
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private FormDataService formDataService;
	
	@Autowired
	private ApplicationWorkFlowService applicationWorkFlowService;
	
	@Autowired
	private CollectionAccountService collectionAccountService;
	
	@Value("${rabbitmq.ircs.incoming.exchange}")
	private String messageInExchange;
	
	@Value("${rabbitmq.routingkey.prefix}")
	private String routingKeyPrefix;
	
	@Value("${rabbitmq.ircs.si.payment.incoming.routingkey}")
	private String paymentInRoutingKey;
	
	@Value("${app.validate.client.system.host}")
	private Boolean validateRequestHost;
	
	@Autowired
	private Utils utils;
	
	@Autowired
	private PKIUtils pkiUtils;
	
	@Autowired
	private MessageSource messageSource;
	
	@PostMapping("/submit/request-v1")
	public String receivePaymentSubmissionRequest(@RequestBody String requestContent, @RequestHeader Map<String, String> requestHeaders
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
		String clientCode = "";
		Integer logLevel = 0;
		Long apiVersionNumber = 1L;
		
		String requestId = utils.getStringWithinXmlTagExclusive(requestContent, "reqid");
		PaymentNotificationApiAckBodyDto paymentNotificationApiAck = null;
		CommunicationApiDetailsDto commApi = null;
		url = request.getRequestURI();
		String requestIp = utils.getRequestIP(request);
		String requestProtocol = request.getProtocol();
		Logger loggerPaymentNotification= LogManager.getLogger("PaymentNotification");
		loggerPaymentNotification.info("Event : Payment Notification Api Request,EventId :"+processRequestId+",RequestId :"+requestId+""
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
//						InputStream requestXsdSchema = getClass().getResourceAsStream("/templates/xsd-templates/PaymentNotificationV1.xsd");
//						Map<String,String> schemaValidationResult = utils.validateRequestXMLSchema(requestXsdSchema, requestContent);
//						if (schemaValidationResult.get("code").equals("1001")) {
							
							// Read xml 
							JAXBContext jaxbContext = JAXBContext.newInstance(PaymentNotificationApiReqDto.class);
							Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
							StringReader stringReader = new StringReader(requestContent);
							PaymentNotificationApiReqDto paymentNotificatioApiReq = (PaymentNotificationApiReqDto) unmarshaller.unmarshal(stringReader);
							
							String requestMessage = utils.getStringWithinXml(requestContent, "payment");
							String requestSignature = paymentNotificatioApiReq.getMessageHash();
							PaymentNotificationApiReqBodyDto paymentNotificationApiReqBody = paymentNotificatioApiReq.getMessageBody();
							PaymentNotificationApiReqHeaderDto paymentNotificationApiReqHeader = paymentNotificationApiReqBody.getPaymentHeader();
						
							try {
								
								commApi = communicationApiService.findByClientIdClientKeyAndApiCategoryIdAndApiVersionNumber(clientCode
										,clientKey,Constants.API_CATEG_SI_PAYMENT_NOTIFICATION,apiVersionNumber);

								if(commApi != null) {
									
									if(commApi.getApiStatusId().equals(Constants.RECORD_STATUS_ACTIVE)) {
										
										if(commApi.getInboundStatusId().equals(Constants.RECORD_STATUS_ACTIVE)) {
											
											if(commApi.getSystemClientStatusId().equals(Constants.RECORD_STATUS_ACTIVE)) {
												
												if(commApi.getCertificateStatusId().equals(Constants.RECORD_STATUS_ACTIVE)) {
													
													String certificateFile = pkiUtils.appClientKeyFilePath + commApi.getCertificateFilename();
													String certificatePassPhrase = commApi.getCertificatePassphrase();
													String certificateAlias = commApi.getCertificateAlias();
													Long sigAlg = commApi.getSignatureAlgo();
													
													if (utils.isFileExist(certificateFile)){
														
														if(!utils.isNullOrEmpty(certificatePassPhrase) && !utils.isNullOrEmpty(certificateAlias)) {
															
															if (pkiUtils.verifySignature(requestSignature,requestMessage,certificatePassPhrase,certificateAlias,
																	certificateFile,sigAlg)) {
																
																List<Invoice> invoiceList = invoiceService.findByPaymentNumber(paymentNotificationApiReqHeader.getPaymentNumber());
																if(invoiceList != null && !invoiceList.isEmpty()) {
																	
																	List<PaymentNotificationApiReqDetailDto> paymentDetailList =  paymentNotificationApiReqBody.getPaymentDetail().getPaymentDetailList();
																	List<PaymentNotificationApiReqDetailDto> processedPaymentDetailList = new ArrayList<>();
																	List<PaymentNotificationApiReqDetailDto> finalPaymentDetailList = new ArrayList<>();
																	for(PaymentNotificationApiReqDetailDto paymentDetail : paymentDetailList) {
																		
																		List<String> processStatus = new ArrayList<>();
																		List<String> processStatusDescription = new ArrayList<>();
																		CollectionAccount collAccount = collectionAccountService.
																				findByCollectionAccountAndCurrencyCodeAndServiceInstitutionCodeAndPaymentFacilitatorCode(
																				paymentDetail.getCollectionAccount(), paymentDetail.getCurrency(), paymentDetail.getServiceInstitution(),
																				paymentNotificationApiReqHeader.getPaymentFacilitator());
																		if(collAccount == null || !collAccount.getRecordStatusId().equals(Constants.RECORD_STATUS_ACTIVE)) {
																			processStatus.add(messageSource.getMessage("code.1032", null, currentLocale));
																			processStatusDescription.add(messageSource.getMessage("message.1032", null, currentLocale));
																		}
																		
																		for(Invoice invoice : invoiceList) {
																			if(invoice.getInvoicePaymentNumber().equals(paymentDetail.getInvoicePaymentNumber())) {
																				
																				Map<String, String> paymentResult = validatePaymentAmount(paymentDetail,invoice);
																				
																				paymentDetail.setIsPaid(Boolean.valueOf(paymentResult.get("isPaid")));
																				processStatus.add(messageSource.getMessage("code."+paymentResult.get("statusCode"), null, currentLocale));
																				processStatusDescription.add(messageSource.getMessage("code."+paymentResult.get("statusCode"), null, currentLocale));
																				break;
																			}
																		}
																		
																		paymentDetail.setProcessStatus(processStatus);
																		paymentDetail.setProcessStatusDescription(processStatusDescription);
																		paymentDetail.setPaymentFacilitatorId(collAccount.getPaymentFacilitator().getId());
																		paymentDetail.setCollectionAccountId(collAccount.getId());
																		processedPaymentDetailList.add(paymentDetail);
																	}
																	
																	if(processedPaymentDetailList.size() == paymentDetailList.size()) {
																		
																		for(PaymentNotificationApiReqDetailDto paymentDetail : processedPaymentDetailList) {
																			
																			for(Invoice invoice : invoiceList) {
																				
																				if(invoice.getInvoicePaymentNumber().equals(paymentDetail.getInvoicePaymentNumber())) {
																					
																					if(paymentDetail.getProcessStatus().contains(Constants.DEFAULT_SUCCESS_STATUS)) {
																						/**
																						 * Update invoice
																						 */
																						BigDecimal totalPaidAmount = invoice.getPaidAmount().add(paymentDetail.getPaymentAmount());
																						invoice.setIsInvoicePaid(paymentDetail.getIsPaid());
																						invoice.setPaidAmount(totalPaidAmount);
																						Invoice updatedInvoice = invoiceService.saveInvoice(invoice);
																						
																						paymentDetail.setInvoice(invoice);
																						finalPaymentDetailList.add(paymentDetail);
																						
																						/**
																						 * Update reference
																						 */
																						Optional<FormData1> application = formDataService.findFormData1ById(Long.parseLong(updatedInvoice.getReference()));
																		    			if(application.isPresent()) {
																		    				
																		    				if(updatedInvoice.getIsInvoicePaid()) {
																		    					application.get().setWorkFlowActionName(Constants.ACTION_PAYMENT_PAID);
																			    				application.get().setWorkFlowActionId(Constants.ACTION_PAYMENT_PAID_ID);
																		    				} else {
																		    					application.get().setWorkFlowActionName(Constants.ACTION_PAYMENT_ONPROGRESS);
																			    				application.get().setWorkFlowActionId(Constants.ACTION_PAYMENT_ONPROGRESS_ID);
																		    				}
																		    				
																		    				application.get().setWorkFlowName(Constants.WORK_FLOW_PAYMENT);
																		    				application.get().setWorkFlowId(Constants.WORK_FLOW_PAYMENT_ID);
																		    				application.get().setUpdatedBy(Constants.DEFAULT_SYS_USERID);
																		    				application.get().setUpdatedByUserName(Constants.DEFAULT_SYS_USERNAME);
																		    				application.get().setUpdatedDate(new Date());
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
																				}
																			}
																		}
																		
																		String routingKeySuffix = paymentInRoutingKey.substring(paymentInRoutingKey.indexOf("*")+1);
																		String routingKey = routingKeyPrefix+"."+clientCode+routingKeySuffix;
																		Map<String,String> amqHeaders = new HashMap<String,String>();
																		amqHeaders.put("receivedTime", receivedTime);
																		amqHeaders.put("requestId", requestId);
																		amqHeaders.put("processRequestId", processRequestId);
																		amqHeaders.put("clientCode", clientCode);
																		amqHeaders.put("apiVersion",String.valueOf(apiVersionNumber));
																		
																		System.out.println("routingKey*****************"+routingKey);
																		PaymentNotificationApiReqBodyDto processedPaymentBody = new PaymentNotificationApiReqBodyDto(paymentNotificationApiReqHeader,
																				new PaymentNotificationApiReqDetailsDto(finalPaymentDetailList));
																		utils.publishMsgToExchange(messageInExchange,routingKey,processedPaymentBody,amqHeaders);
																		
																		acknowledgementStatusCode = messageSource.getMessage("code.1001", null, currentLocale);
																		acknowledgementStatusDesc = messageSource.getMessage("message.1001", null, currentLocale);
																		logLevel = 2;
																		remark = "Successful";
																		paymentNotificationApiAck = new PaymentNotificationApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
																				,acknowledgementStatusDesc);
																	}
																} else {
																	logLevel = 5;
																	acknowledgementStatusCode = messageSource.getMessage("code.1028", null, currentLocale);
																	acknowledgementStatusDesc = messageSource.getMessage("message.1028", null, currentLocale);
																	remark = "Invalid payment number or invoice does not exists.";
																	paymentNotificationApiAck = new PaymentNotificationApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
																			,acknowledgementStatusDesc);
																}
															} else {
																logLevel = 4;
																acknowledgementStatusCode = messageSource.getMessage("code.1016", null, currentLocale);
																acknowledgementStatusDesc = messageSource.getMessage("message.1016", null, currentLocale);
																remark = "Invalid message signature";
																paymentNotificationApiAck = new PaymentNotificationApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
																		,acknowledgementStatusDesc);
															}
														} else {
															logLevel = 5;
															acknowledgementStatusCode = messageSource.getMessage("code.1015", null, currentLocale);
															acknowledgementStatusDesc = messageSource.getMessage("message.1015", null, currentLocale);
															remark = "Bad Client PKI configurations";
															paymentNotificationApiAck = new PaymentNotificationApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
																	,acknowledgementStatusDesc);
														}
													} else {
														logLevel = 5;
														acknowledgementStatusCode = messageSource.getMessage("code.1014", null, currentLocale);
														acknowledgementStatusDesc = messageSource.getMessage("message.1014", null, currentLocale);
														remark = "Client PKI Certificate File is Missing or configured wrongly";
														paymentNotificationApiAck = new PaymentNotificationApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
																,acknowledgementStatusDesc);
													}
												} else {
													logLevel = 4;
													acknowledgementStatusCode = messageSource.getMessage("code.1040", null, currentLocale);
													acknowledgementStatusDesc = messageSource.getMessage("message.1040", null, currentLocale);
													remark = "Inactive or invalid client system pki certificate";
													paymentNotificationApiAck = new PaymentNotificationApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
															,acknowledgementStatusDesc);
												}
											} else {
												logLevel = 4;
												acknowledgementStatusCode = messageSource.getMessage("code.1039", null, currentLocale);
												acknowledgementStatusDesc = messageSource.getMessage("message.1039", null, currentLocale);
												remark = "Client is prohibited from utilizing the request system.";
												paymentNotificationApiAck = new PaymentNotificationApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
														,acknowledgementStatusDesc);
											}
										} else {
											logLevel = 4;
											acknowledgementStatusCode = messageSource.getMessage("code.1051", null, currentLocale);
											acknowledgementStatusDesc = messageSource.getMessage("message.1051", null, currentLocale);
											remark = "Client inbound communication not allowed";
											paymentNotificationApiAck = new PaymentNotificationApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
													,acknowledgementStatusDesc);
										}
									} else {
										logLevel = 4;
										acknowledgementStatusCode = messageSource.getMessage("code.1012", null, currentLocale);
										acknowledgementStatusDesc = messageSource.getMessage("message.1012", null, currentLocale);
										remark = "Inactive client configuration";
										paymentNotificationApiAck = new PaymentNotificationApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
												,acknowledgementStatusDesc);
									}
								} else {
									logLevel = 5;
									acknowledgementStatusCode = messageSource.getMessage("code.1011", null, currentLocale);
									acknowledgementStatusDesc = messageSource.getMessage("message.1011", null, currentLocale);
									remark = "No client configuration found";
									paymentNotificationApiAck = new PaymentNotificationApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
											,acknowledgementStatusDesc);
								}
							} catch(Exception ex) {
								ex.printStackTrace();
								logLevel = 5;
								acknowledgementStatusCode = messageSource.getMessage("code.1006", null, currentLocale);
								acknowledgementStatusDesc = messageSource.getMessage("message.1006", null, currentLocale);
								remark = "An error occured while processing request,"+ex.getMessage();
								paymentNotificationApiAck = new PaymentNotificationApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
										,acknowledgementStatusDesc);
							}
//						} else {
//							
//							logLevel = 4;
//							acknowledgementStatusCode = schemaValidationResult.get("code");
//							acknowledgementStatusDesc = schemaValidationResult.get("remark");
//							remark = "Invalid request data, "+schemaValidationResult.get("message");
//							paymentNotificationApiAck = new PaymentNotificationApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
//									,acknowledgementStatusDesc);
//						}
					} else {
						logLevel = 5;
						acknowledgementStatusCode = messageSource.getMessage("code.1038", null, currentLocale);
						acknowledgementStatusDesc = messageSource.getMessage("message.1038", null, currentLocale);
						remark = "Invalid client system host";
						paymentNotificationApiAck = new PaymentNotificationApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
								,acknowledgementStatusDesc);
					}
				} else {
					
					logLevel = 5;
					acknowledgementStatusCode = messageSource.getMessage("code.1008", null, currentLocale);
					acknowledgementStatusDesc = messageSource.getMessage("message.1008", null, currentLocale);
					remark = "Missing or invalid request header";
					paymentNotificationApiAck = new PaymentNotificationApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
							,acknowledgementStatusDesc);
				}
			} else {
				
				logLevel = 5;
				acknowledgementStatusCode = messageSource.getMessage("code.1008", null, currentLocale);
				acknowledgementStatusDesc = messageSource.getMessage("message.1008", null, currentLocale);
				remark = "Missing or invalid request header";
				paymentNotificationApiAck = new PaymentNotificationApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
						,acknowledgementStatusDesc);
			}
		} catch(Exception e) {
			e.printStackTrace();
			logLevel = 4;
			acknowledgementStatusCode = messageSource.getMessage("code.1002", null, currentLocale);
			acknowledgementStatusDesc = messageSource.getMessage("message.1002", null, currentLocale);
			remark = "An error occured while request processing, "+e.getMessage();
			paymentNotificationApiAck = new PaymentNotificationApiAckBodyDto(processRequestId,requestId,acknowledgementStatusCode
					,acknowledgementStatusDesc);
		}
		return preparePaymentNotificationApiAck(paymentNotificationApiAck,commApi,currentLocale,processRequestId,requestId,remark,url,clientCode,clientKey,logLevel);
	}
		
	
	/**
	 * Prepare Payment notification api request acknowledgement
	 * @param paymentNotificationApiAck
	 * @param commApi 
	 * @param currentLocale
	 * @param applySecondaryKey
	 * @param processRequestId
	 * @param requestId
	 * @param remark
	 * @param url
	 * @param clientCode
	 * @param logLevel
	 * @return String
	 */
	private String preparePaymentNotificationApiAck(PaymentNotificationApiAckBodyDto paymentNotificationApiAck,
			CommunicationApiDetailsDto commApi, Locale currentLocale, String processRequestId, String requestId, String remark,
			String url, String clientCode, String clientKey, Integer logLevel) {
		
		String signedResponse = "";
		String signature = "";
		String requestResponse = "";
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		String acknowledgeTime = utils.getLocalDateTime(new Date()).format(dateTimeFormatter);
		Logger loggerPaymentSubmission= LogManager.getLogger("PaymentSubmission");
		
		try {
			
			requestResponse = utils.generateXmlString(JAXBContext.newInstance(PaymentNotificationApiAckBodyDto.class),paymentNotificationApiAck);
			requestResponse = utils.getStringWithinXml(requestResponse, "payment");
			
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
			
			PaymentNotificationApiAckDto notificationApiAck = new PaymentNotificationApiAckDto(paymentNotificationApiAck,signature);
			signedResponse = utils.generateXmlString(JAXBContext.newInstance(PaymentNotificationApiAckDto.class),notificationApiAck);
	
			if(logLevel == Constants.LOG_ERROR) {
				//log error 
				loggerPaymentSubmission.error("\nEvent : Payment Notification Api Acknowledgement,EventId :"+processRequestId+",RequestId :"+requestId+",AcknowledgementTime :"+acknowledgeTime+""
						+ "\nClient : "+clientCode+",ClientKey :"+clientKey+",At Url :"+url+"\nRemark :"+remark+",\nAcknowledgementContent :"+signedResponse);
			}else if(logLevel == Constants.LOG_FATAL) {
				//log fatal  
				loggerPaymentSubmission.fatal("\nEvent : Payment Notification Api Acknowledgement,EventId :"+processRequestId+",RequestId :"+requestId+",AcknowledgementTime :"+acknowledgeTime+""
						+ "\nClient : "+clientCode+",ClientKey :"+clientKey+",At Url :"+url+"\nRemark :"+remark+",\nAcknowledgementContent :"+signedResponse);
			}else {
				//log info
				loggerPaymentSubmission.info("\nEvent : Payment Notification Api Acknowledgement,EventId :"+processRequestId+",RequestId :"+requestId+",AcknowledgementTime :"+acknowledgeTime+""
						+ "\nClient : "+clientCode+",ClientKey :"+clientKey+",At Url :"+url+"\nRemark :"+remark+",\nAcknowledgementContent :"+signedResponse);
			}
		}catch(Exception ex) {
			//log error
			remark = ex.getMessage();
			loggerPaymentSubmission.error("\nEvent : Payment Notification Api Acknowledgement,EventId :"+processRequestId+",RequestId :"+requestId+",AcknowledgementTime :"+acknowledgeTime+""
					+ "\nClient : "+clientCode+",ClientKey :"+clientKey+",At Url :"+url+"\nRemark :"+remark+",\nAcknowledgementContent :"+signedResponse);
			ex.printStackTrace();
		}
		
		return signedResponse;
	}
	

	/**
	 * Validate payment amount
	 * @param paymentSubmissionApiReqDetail
	 * @param invoice
	 * @return Map<String, String>
	 */
	private Map<String, String> validatePaymentAmount(PaymentNotificationApiReqDetailDto paymentSubmissionApiReqDetail,
			Invoice invoice) {
		
		Map<String,String> amountPaidResult = new ConcurrentHashMap<>();
		String isPaid = "false";
		String statusCode = "1029";
		if(invoice.getPaymentOptionId().equals(Constants.PAY_OPT_COMPLETE_ID)) {
			
			if(invoice.getInvoiceAmount().compareTo(paymentSubmissionApiReqDetail.getPaymentAmount()) <= 0) {
				isPaid = "true";
				statusCode = "1001";
			}
		} else if(invoice.getPaymentOptionId().equals(Constants.PAY_OPT_PRECISE_ID)) {
			
			if(invoice.getInvoiceAmount().compareTo(paymentSubmissionApiReqDetail.getPaymentAmount()) == 0) {
				isPaid = "true";
				statusCode = "1001";
			} 
		} else if(invoice.getPaymentOptionId().equals(Constants.PAY_OPT_PARTIAL_ID)) {
			
			BigDecimal invoiceBalance = invoice.getInvoiceAmount().subtract(invoice.getPaidAmount());
			if(invoice.getPaidAmount().compareTo(new BigDecimal("0.00")) == 0) {
				if(invoice.getMinimumPaymentAmount().compareTo(paymentSubmissionApiReqDetail.getPaymentAmount()) <= 0) {
					statusCode = "1001";
				}
			} else {
				if(Constants.DEFAULT_MIN_AMOUNT_PAYABLE.compareTo(paymentSubmissionApiReqDetail.getPaymentAmount()) <= 0) {
					statusCode = "1001";
				}
			}
			if(invoiceBalance.compareTo(paymentSubmissionApiReqDetail.getPaymentAmount()) <= 0) {
					
				isPaid = "true";
			} 
		} else if(invoice.getPaymentOptionId().equals(Constants.PAY_OPT_LIMITED_ID)) {
			
			BigDecimal invoiceBalance = invoice.getInvoiceAmount().subtract(invoice.getPaidAmount());
			if(invoice.getPaidAmount().compareTo(new BigDecimal("0.00")) == 0) {
				if(invoice.getMinimumPaymentAmount().compareTo(paymentSubmissionApiReqDetail.getPaymentAmount()) <= 0) {
					statusCode = "1001";
				}
			} else {
				if(Constants.DEFAULT_MIN_AMOUNT_PAYABLE.compareTo(paymentSubmissionApiReqDetail.getPaymentAmount()) <= 0) {
					statusCode = "1001";
				}
			}
			if(invoiceBalance.compareTo(paymentSubmissionApiReqDetail.getPaymentAmount()) == 0) {
					
				isPaid = "true";
			} 
		} else {
			if(invoice.getMinimumPaymentAmount().compareTo(paymentSubmissionApiReqDetail.getPaymentAmount()) <= 0) {
				statusCode = "1001";
			} 
		}
		amountPaidResult.put("isPaid", isPaid);
		amountPaidResult.put("statusCode", statusCode);
		
		return amountPaidResult;
	}
}
