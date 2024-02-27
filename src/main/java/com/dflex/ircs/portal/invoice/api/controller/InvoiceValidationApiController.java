package com.dflex.ircs.portal.invoice.api.controller;

import java.io.InputStream;
import java.io.StringReader;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dflex.ircs.portal.auth.dto.ClientDetailsDto;
import com.dflex.ircs.portal.auth.dto.CommunicationApiDetailsDto;
import com.dflex.ircs.portal.auth.service.CommunicationApiService;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceSubmissionApiReqDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceSubmissionApiResDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceValidationApiReqBodyDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceValidationApiReqDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceValidationApiResBodyDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceValidationApiResDetailDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceValidationApiResDetailsDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceValidationApiResDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceValidationApiResHeaderDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceValidationApiResServiceDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceValidationApiResServicesDto;
import com.dflex.ircs.portal.invoice.entity.Invoice;
import com.dflex.ircs.portal.invoice.entity.InvoiceItem;
import com.dflex.ircs.portal.invoice.service.InvoiceItemService;
import com.dflex.ircs.portal.invoice.service.InvoiceService;
import com.dflex.ircs.portal.setup.service.OtherServiceInstitutionService;
import com.dflex.ircs.portal.setup.service.ServiceInstitutionService;
import com.dflex.ircs.portal.util.Constants;
import com.dflex.ircs.portal.util.PKIUtils;
import com.dflex.ircs.portal.util.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

/**
 * 
 * @author Augustino Mwageni
 * 
 * Receive Invoice Requests
 *
 */
@RestController
@RequestMapping("/api/invoice")
public class InvoiceValidationApiController {
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private InvoiceItemService invoiceItemService;
	
	@Autowired
	private CommunicationApiService communicationApiService;
	
	@Autowired
	private ServiceInstitutionService serviceInstitutionService;
	
	@Autowired
	private OtherServiceInstitutionService otherServiceInstitutionService;
	
	@Autowired
	private Utils utils;
	
	@Autowired
	private PKIUtils pkiUtils;
	
	@Autowired
	private MessageSource messageSource;

	protected org.slf4j.Logger logger = LoggerFactory.getLogger(InvoiceValidationApiController.class);
	
	
	/**
	 * Invoice Details Validation Api for Portal Core Clients
	 * @param requestContent
	 * @param requestHeaders
	 * @param request
	 * @return String
	 */	
	@PostMapping("/validation-v1")
	public String receiveInvoiceValidationRequest(@RequestBody String requestContent, @RequestHeader Map<String, String> 	requestHeaders
			,HttpServletRequest request) {
		
		Locale currentLocale = LocaleContextHolder.getLocale();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		String receivedTime = utils.getLocalDateTime(new Date()).format(dateTimeFormatter);
		String processRequestId = utils.getToken();
		String responseStatusCode = "";
		String responseStatusDesc = "";
		String remark = "";
		String url = "";
		Integer logLevel = 0;
		Long apiVersionNumber = 1L;
		
		String invoiceType = "";
		String detailCount = "";
		String clientKey = "";
		String clientCode = "";
		
		String requestId = utils.getStringWithinXmlTagExclusive(requestContent, "requestid");
		String paymentNumber = utils.getStringWithinXmlTagExclusive(requestContent, "paymentnumber");
		String requestIp = utils.getRequestIP(request);
		String requestProtocol = request.getProtocol();
		CommunicationApiDetailsDto commApi = null;
		InvoiceValidationApiResBodyDto invoiceValidationApiRes = null;
		InvoiceValidationApiResDetailsDto invoiceDetail = null;
		InvoiceValidationApiResHeaderDto invoiceValidationApiResHeader = null;
		List<InvoiceValidationApiResDetailDto> invoiceDetailList = new ArrayList<>();
		url = request.getRequestURI();
		
		Logger loggerInvoiceValidation = LogManager.getLogger("InvoiceValidation");
		loggerInvoiceValidation.info("Event : Invoice Validation Api Request,EventId :"+processRequestId+",RequestId :"+requestId+""
				+ ",ReceivedTime :"+receivedTime+"\nReceivedHeaders : "+requestHeaders+",\nAt Url :"+url+" ,RequestIp :"+requestIp+","
						+ " RequestProtocol :"+requestProtocol+", \nReceivedContent :"+requestContent+"\n");
		
		try {
			
			if (requestHeaders.containsKey("clientkey") && requestHeaders.containsKey("clientcode")) {
				
				if(!utils.isNullOrEmpty(requestHeaders.get("clientcode")) && !utils.isNullOrEmpty(requestHeaders.get("clientkey"))) {
					
					clientKey = requestHeaders.get("clientkey");
					clientCode = requestHeaders.get("clientcode");
					
					// Validate Incoming Request Schema
					InputStream requestXsdSchema = getClass().getResourceAsStream("/templates/xsd-templates/InvoiceValidationV1.xsd");
					Map<String,String> schemaValidationResult = utils.validateRequestXMLSchema(requestXsdSchema, requestContent);
					if (schemaValidationResult.get("code").equals("1001")) {
						
						// Read xml 
						JAXBContext jaxbContext = JAXBContext.newInstance(InvoiceValidationApiReqDto.class);
						Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
						StringReader stringReader = new StringReader(requestContent);
						InvoiceValidationApiReqDto invoiceValidationApiReq = (InvoiceValidationApiReqDto) unmarshaller.unmarshal(stringReader);
						
						String requestMessage = utils.getStringWithinXml(requestContent, "invoice");
						String requestSignature = invoiceValidationApiReq.getMessageHash();
						InvoiceValidationApiReqBodyDto invoiceValidationApiReqBody = invoiceValidationApiReq.getMessageBody();
						
						try {
							
							Long clientCategoryId = clientCode.startsWith(Constants.SERVICE_OTHER_INSTITUTION_PREFIX) ? Constants.CLIENT_CATEGORY_OTHER_SERVICE_INSTITUTION
									: Constants.CLIENT_CATEGORY_SERVICE_INSTITUTION;
							ClientDetailsDto client = null;
							if(clientCategoryId.equals(Constants.CLIENT_CATEGORY_SERVICE_INSTITUTION)) {
								client = serviceInstitutionService.findByClientCodeAndRecordStatusId(clientCode, Constants.RECORD_STATUS_ACTIVE);
							} else {
								client = otherServiceInstitutionService.findByClientCodeAndRecordStatusId(clientCode, Constants.RECORD_STATUS_ACTIVE);
							}
							
							if(client != null) {
								
								commApi = communicationApiService.findByClientIdClientKeyAndApiCategoryIdAndApiVersionNumber(clientCode
										,clientKey,Constants.API_CATEG_INVOICE_VALIDATION_REQ,apiVersionNumber);
								if(commApi != null) {
									
									if(commApi.getApiStatusId().equals(Constants.RECORD_STATUS_ACTIVE)) {
										
										if(commApi.getInboundStatusId().equals(Constants.RECORD_STATUS_ACTIVE)) {
											
											if(commApi.getSystemClientStatusId().equals(Constants.RECORD_STATUS_ACTIVE)) {
												
												if(commApi.getCertificateStatusId().equals(Constants.RECORD_STATUS_ACTIVE)) {
													
													String certificateFile = pkiUtils.appClientKeyFilePath + commApi.getCertificateFilename();
													String certificatePassPhrase = commApi.getCertificatePassphrase();
													String certificateAlias = commApi.getCertificateAlias();
													
													if (utils.isFileExist(certificateFile)){
														
														if(!utils.isNullOrEmpty(certificatePassPhrase) && !utils.isNullOrEmpty(certificateAlias)) {
															
															if (pkiUtils.verifySignature(requestSignature, requestMessage,certificatePassPhrase, certificateAlias, certificateFile)) {
																
																List<Invoice> invoiceList = invoiceService.findByPaymentNumber(invoiceValidationApiReqBody.getInvoiceType());
																if(invoiceList != null && !invoiceList.isEmpty()) {

																	invoiceType = String.valueOf(invoiceList.get(0).getInvoiceTypeId());
																	invoiceList.forEach(invoice -> {
																		
																		List<InvoiceItem>invoiceItemList = invoiceItemService.findByInvoiceIdAndRecordStatusId(invoice.getId(),
																				Constants.RECORD_STATUS_ACTIVE);
																		if(invoiceItemList != null && !invoiceItemList.isEmpty()) {
																			
																			List<InvoiceValidationApiResServiceDto> serviceList = new ArrayList<>();
																			invoiceItemList.forEach(invoiceItem -> {
																				serviceList.add(new InvoiceValidationApiResServiceDto(invoiceItem.getServiceTypeCode(),
																						invoiceItem.getServiceReference(),invoiceItem.getServiceTypeName()
																						,invoiceItem.getServiceAmount()));
																			});
																			
																			InvoiceValidationApiResServicesDto invoiceServices = new InvoiceValidationApiResServicesDto(serviceList);
																		
																			invoiceDetailList.add(new InvoiceValidationApiResDetailDto(
																					invoice.getServiceInstitutionCode(),invoice.getServiceInstitution().getInstitutionName(),invoice.getInvoicePaymentNumber(),
																					invoice.getInvoiceDescription(),invoice.getCustomerAccount(),invoice.getCustomerName(),invoice.getCustomerPhoneNumber(),
																					invoice.getCustomerEmail(),utils.getLocalDateTime(invoice.getInvoiceIssueDate()).format(dateTimeFormatter),
																					utils.getLocalDateTime(invoice.getInvoiceExpiryDate()).format(dateTimeFormatter),invoice.getInvoiceAmount(),
																					invoice.getMinimumPaymentAmount(),invoice.getPaidAmount(),invoice.getCurrencyCode()
																					,invoice.getInvoicePayPlanName(),invoice.getPaymentOptionName(),String.valueOf(invoice.getIsInvoicePaid()),invoiceServices));
																		}
																	});
																	
																	invoiceDetail = new InvoiceValidationApiResDetailsDto(invoiceDetailList);
																	
																	detailCount = String.valueOf(invoiceDetailList.size());
																	
																	responseStatusCode = messageSource.getMessage("code.1001", null, currentLocale);
																	responseStatusDesc = messageSource.getMessage("message.1001", null, currentLocale);
																	logLevel = 2;
																	remark = "Successful";
																	invoiceValidationApiResHeader = new InvoiceValidationApiResHeaderDto(processRequestId,requestId,paymentNumber,invoiceType
																			,detailCount,responseStatusCode,responseStatusDesc);
																	
																} else {
																	responseStatusCode = messageSource.getMessage("code.1028", null, currentLocale);
																	responseStatusDesc = messageSource.getMessage("message.1028", null, currentLocale);
																	logLevel = 4;
																	remark = responseStatusDesc;
																	invoiceValidationApiResHeader = new InvoiceValidationApiResHeaderDto(processRequestId,requestId,paymentNumber,invoiceType
																			,detailCount,responseStatusCode,responseStatusDesc);
																}
															} else {
																logLevel = 4;
																responseStatusCode = messageSource.getMessage("code.1016", null, currentLocale);
																responseStatusDesc = messageSource.getMessage("message.1016", null, currentLocale);
																remark = "Invalid message signature";
																invoiceValidationApiResHeader = new InvoiceValidationApiResHeaderDto(processRequestId,requestId,paymentNumber,invoiceType
																		,detailCount,responseStatusCode,responseStatusDesc);
															}
														} else {
															logLevel = 5;
															responseStatusCode = messageSource.getMessage("code.1015", null, currentLocale);
															responseStatusDesc = messageSource.getMessage("message.1015", null, currentLocale);
															remark = "Bad Client PKI configurations";
															invoiceValidationApiResHeader = new InvoiceValidationApiResHeaderDto(processRequestId,requestId,paymentNumber,invoiceType
																	,detailCount,responseStatusCode,responseStatusDesc);
														}
													} else {
														logLevel = 5;
														responseStatusCode = messageSource.getMessage("code.1014", null, currentLocale);
														responseStatusDesc = messageSource.getMessage("message.1014", null, currentLocale);
														remark = "Client PKI Certificate File is Missing or configured wrongly";
														invoiceValidationApiResHeader = new InvoiceValidationApiResHeaderDto(processRequestId,requestId,paymentNumber,invoiceType
																,detailCount,responseStatusCode,responseStatusDesc);
													}
												} else {
													logLevel = 4;
													responseStatusCode = messageSource.getMessage("code.1040", null, currentLocale);
													responseStatusDesc = messageSource.getMessage("message.1040", null, currentLocale);
													remark = "Inactive or invalid client system pki certificate";
													invoiceValidationApiResHeader = new InvoiceValidationApiResHeaderDto(processRequestId,requestId,paymentNumber,invoiceType
															,detailCount,responseStatusCode,responseStatusDesc);
												}
											} else {
												logLevel = 4;
												responseStatusCode = messageSource.getMessage("code.1039", null, currentLocale);
												responseStatusDesc = messageSource.getMessage("message.1039", null, currentLocale);
												remark = "Client is prohibited from utilizing the request system.";
												invoiceValidationApiResHeader = new InvoiceValidationApiResHeaderDto(processRequestId,requestId,paymentNumber,invoiceType
														,detailCount,responseStatusCode,responseStatusDesc);
											}
										} else {
											logLevel = 4;
											responseStatusCode = messageSource.getMessage("code.1051", null, currentLocale);
											responseStatusDesc = messageSource.getMessage("message.1051", null, currentLocale);
											remark = "Client inbound communication not allowed";
											invoiceValidationApiResHeader = new InvoiceValidationApiResHeaderDto(processRequestId,requestId,paymentNumber,invoiceType
													,detailCount,responseStatusCode,responseStatusDesc);
										}
									} else {
										logLevel = 4;
										responseStatusCode = messageSource.getMessage("code.1012", null, currentLocale);
										responseStatusDesc = messageSource.getMessage("message.1012", null, currentLocale);
										remark = "Inactive client configuration";
										invoiceValidationApiResHeader = new InvoiceValidationApiResHeaderDto(processRequestId,requestId,paymentNumber,invoiceType
												,detailCount,responseStatusCode,responseStatusDesc);
									}
								} else {
									logLevel = 5;
									responseStatusCode = messageSource.getMessage("code.1011", null, currentLocale);
									responseStatusDesc = messageSource.getMessage("message.1011", null, currentLocale);
									remark = "No client configuration found";
									invoiceValidationApiResHeader = new InvoiceValidationApiResHeaderDto(processRequestId,requestId,paymentNumber,invoiceType
											,detailCount,responseStatusCode,responseStatusDesc);
								}
							} else {
								logLevel = 4;
								responseStatusCode = messageSource.getMessage("code.1013", null, currentLocale);
								responseStatusDesc = messageSource.getMessage("message.1013", null, currentLocale);
								remark = "Invalid client";
								invoiceValidationApiResHeader = new InvoiceValidationApiResHeaderDto(processRequestId,requestId,paymentNumber,invoiceType
										,detailCount,responseStatusCode,responseStatusDesc);
							}
						} catch(Exception ex) {
							ex.printStackTrace();
							logLevel = 5;
							responseStatusCode = messageSource.getMessage("code.1010", null, currentLocale);
							responseStatusDesc = messageSource.getMessage("message.1010", null, currentLocale);
							remark = "Internal communication failure,"+ex.getMessage();
							invoiceValidationApiResHeader = new InvoiceValidationApiResHeaderDto(processRequestId,requestId,paymentNumber,invoiceType
									,detailCount,responseStatusCode,responseStatusDesc);
						}
					} else {
						
						logLevel = 4;
						responseStatusCode = schemaValidationResult.get("code");
						responseStatusDesc = schemaValidationResult.get("remark");
						remark = "Invalid request data, "+schemaValidationResult.get("message");
						invoiceValidationApiResHeader = new InvoiceValidationApiResHeaderDto(processRequestId,requestId,paymentNumber,invoiceType
								,detailCount,responseStatusCode,responseStatusDesc);
					}
				} else {
					
					logLevel = 5;
					responseStatusCode = messageSource.getMessage("code.1008", null, currentLocale);
					responseStatusDesc = messageSource.getMessage("message.1008", null, currentLocale);
					remark = "Missing or invalid request header";
					invoiceValidationApiResHeader = new InvoiceValidationApiResHeaderDto(processRequestId,requestId,paymentNumber,invoiceType
							,detailCount,responseStatusCode,responseStatusDesc);
				}
			} else {
				
				logLevel = 5;
				responseStatusCode = messageSource.getMessage("code.1008", null, currentLocale);
				responseStatusDesc = messageSource.getMessage("message.1008", null, currentLocale);
				remark = "Missing or invalid request header";
				invoiceValidationApiResHeader = new InvoiceValidationApiResHeaderDto(processRequestId,requestId,paymentNumber,invoiceType
						,detailCount,responseStatusCode,responseStatusDesc);
			}
		} catch(Exception e) {
			e.printStackTrace();
			logLevel = 4;
			responseStatusCode = messageSource.getMessage("code.1002", null, currentLocale);
			responseStatusDesc = messageSource.getMessage("message.1002", null, currentLocale);
			remark = "An error occured while request processing, "+e.getMessage();
			invoiceValidationApiResHeader = new InvoiceValidationApiResHeaderDto(processRequestId,requestId,paymentNumber,invoiceType
					,detailCount,responseStatusCode,responseStatusDesc);
		}
		invoiceValidationApiRes = new InvoiceValidationApiResBodyDto(invoiceValidationApiResHeader,invoiceDetail);
		return prepareInvoiceValidationApiRes(invoiceValidationApiRes,currentLocale,commApi,processRequestId,requestId,remark,url
				,clientCode,clientKey,logLevel);
	}



	/**
	 * Prepare invoice validation api request acknowledgement
	 * @param invoiceValidationApiRes
	 * @param currentLocale
	 * @param commApi 
	 * @param processRequestId
	 * @param requestId
	 * @param remark
	 * @param url
	 * @param clientCode
	 * @param logLevel
	 * @return String
	 */
	private String prepareInvoiceValidationApiRes(InvoiceValidationApiResBodyDto invoiceValidationApiRes,
			Locale currentLocale, CommunicationApiDetailsDto commApi , String processRequestId, String requestId, String remark,
			String url, String clientCode, String clientKey, Integer logLevel) {
		
		String signedResponse = "";
		String signature = "";
		String requestResponse = "";
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		String responseTimeTime = utils.getLocalDateTime(new Date()).format(dateTimeFormatter);
		Logger loggerInvoiceValidation= LogManager.getLogger("InvoiceValidation");
		
		try {
			
			requestResponse = utils.generateXmlString(JAXBContext.newInstance(InvoiceValidationApiResBodyDto.class),invoiceValidationApiRes);
			requestResponse = utils.getStringWithinXml(requestResponse, "invoice");
			if(commApi != null ){
				String appKeyFileName = commApi.getInternalCertificateFilename();
				String appKeyAlias = commApi.getInternalCertificateAlias();
				String appKeyPassphrase = commApi.getInternalCertificatePassphrase();
				String appKeyFile = pkiUtils.appClientKeyFilePath + appKeyFileName;
				
				signature = pkiUtils.createSignature(requestResponse,appKeyPassphrase,appKeyAlias,appKeyFile);
			} else {
				signature = pkiUtils.createSignature(requestResponse,pkiUtils.appPassphrase,pkiUtils.appAlias,pkiUtils.appKeyFile);
			}
			
			InvoiceValidationApiResDto validationApiRes = new InvoiceValidationApiResDto(invoiceValidationApiRes,signature);
			signedResponse = utils.generateXmlString(JAXBContext.newInstance(InvoiceValidationApiResDto.class),validationApiRes);
	
			if(logLevel == Constants.LOG_ERROR) {
				//log error 
				loggerInvoiceValidation.error("\nEvent : Invoice Validation Api Response,EventId :"+processRequestId+",RequestId :"+requestId+",ResponseTime :"+responseTimeTime+""
						+ "\nClient : "+clientCode+",ClientKey :"+clientKey+",At Url :"+url+"\nRemark :"+remark+",\nResponseContent :"+signedResponse);
			}else if(logLevel == Constants.LOG_FATAL) {
				//log fatal  
				loggerInvoiceValidation.fatal("\nEvent : Invoice Validation Api Response,EventId :"+processRequestId+",RequestId :"+requestId+",ResponseTime :"+responseTimeTime+""
						+ "\nClient : "+clientCode+",ClientKey :"+clientKey+",At Url :"+url+"\nRemark :"+remark+",\nResponseContent :"+signedResponse);
			}else {
				//log info
				loggerInvoiceValidation.info("\nEvent : Invoice Validation Api Response,EventId :"+processRequestId+",RequestId :"+requestId+",ResponseTime :"+responseTimeTime+""
						+ "\nClient : "+clientCode+",ClientKey :"+clientKey+",At Url :"+url+"\nRemark :"+remark+",\nResponseContent :"+signedResponse);
			}
		}catch(Exception ex) {
			//log error
			remark = ex.getMessage();
			loggerInvoiceValidation.error("\nEvent : Invoice Validation Api Response,EventId :"+processRequestId+",RequestId :"+requestId+",ResponseTime :"+responseTimeTime+""
					+ "\nClient : "+clientCode+",ClientKey :"+clientKey+",At Url :"+url+"\nRemark :"+remark+",\nResponseContent :"+signedResponse);
			ex.printStackTrace();
		}
		
		return signedResponse;
	}



	@PostMapping("/submission-v1")
	public String invoiceSubmission(@RequestBody InvoiceSubmissionApiReqDto submissionApiRequest) {
		String signedResponse = "";
		String signature = "";
		String requestResponse = "";
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		String responseTimeTime = utils.getLocalDateTime(new Date()).format(dateTimeFormatter);




		try {

			// Read xml
			JAXBContext jaxbContext = JAXBContext.newInstance(InvoiceSubmissionApiResDto.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			//			StringReader stringReader = new StringReader(submissionApiRequest);
			//			InvoiceSubmissionApiReqDto request = (InvoiceSubmissionApiReqDto) unmarshaller.unmarshal(stringReader);
			//
			//			Object invoiceValidationApiReq;
			//			String requestSignature = invoiceValidationApiReq.getMessageHash();
			//			InvoiceValidationApiReqBodyDto invoiceValidationApiReqBody = invoiceValidationApiReq.getMessageBody();
			//			requestResponse = utils.generateXmlString(JAXBContext.newInstance(InvoiceSubmissionApiResDto.class), submissionApiRequest);
			//			requestResponse = utils.getStringWithinXml(requestResponse, "invoice");
			//			signature = pkiUtils.createSignature(requestResponse, pkiUtils.appPassphrase, pkiUtils.appAlias, pkiUtils.appKeyFile);
			//			InvoiceSubmissionApiResDto submissionApiRes = new InvoiceSubmissionApiResDto(submissionApiRequest, signature);
			//			signedResponse = utils.generateXmlString(JAXBContext.newInstance(InvoiceSubmissionApiResDto.class), submissionApiRes);

			logger.info("the invoice for the submission:{} with the signature {}", submissionApiRequest, signature);


			return null;
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}


        /**
         * return all the invoice
         *
         * @return String
         */


/**		@GetMapping(value = "/InvoiceAll")
		public ResponseEntity<List<InvoiceDto>> getAllInvoices() {
			Response<List<InvoiceDto>> response = new Response<>();
			try {
				List<Invoice> invoices = invoiceService.findAll();

				List<InvoiceDto> invoiceDtos = invoices.stream()
						.map(invoice -> new InvoiceDto())//pass relevant fields from 'invoice'
						.collect(Collectors.toList());
                if(invoiceDtos != null && invoiceDtos.size() != 0){
					response.setData(invoiceDtos);
					response.setCode("200");
					logger.info("Invoice Data{}",invoiceDtos);
					response.setMessage("All invoices retrieved successfully");
					return new ResponseEntity<>(invoiceDtos, HttpStatus.OK);
				}else {

					response.setCode("200");
					response.setData(invoiceDtos);
					logger.info("No Invoice Data or Empty: {}", invoiceDtos); // Log no invoice data or empty
					response.setMessage("No Invoice Has been Created.");
					return new ResponseEntity<>(invoiceDtos, HttpStatus.NO_CONTENT);
				}


			} catch (Exception e) {
				logger.error("Error retrieving all invoices", e);
				response.setCode("500");
				response.setMessage("Failed to retrieve all invoices");

				return new ResponseEntity<List<InvoiceDto>>((List<InvoiceDto>) response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

	/**
	 * return invoice
	 *
	 * @return id
	 */
/**	@GetMapping(value = "/InvoiceById/{id}")
	public ResponseEntity<Response<InvoiceDto>> getInvoiceById(@PathVariable("id") Long id) {
		Response<InvoiceDto> response = new Response<>();

		try {
			Optional<Invoice> invoice = invoiceService.findById(id);

			if (invoice.isPresent()) {
				response.setData(invoice);
				response.setCode("200");
				response.setMessage("Invoice retrieved successfully");

				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				response.setCode("404");
				response.setMessage("Invoice not found");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			logger.error("Error retrieving invoice by ID", e);
			response.setCode("500");
			response.setMessage("Failed to retrieve invoice by ID");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			Response res = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,null,request.getRequestURI());
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
		}
	}

	/**
	 * Delete the invoice
	 *
	 * @return id
	 */
/**	@DeleteMapping(value = "/invoiceById/{id}")
	public ResponseEntity<Response<Void>> deleteInvoiceById(@PathVariable("id") Long id) {
		Response<Void> response = new Response<>();
		try {
			this.invoiceService.deleteById(id);
			response.setMessage("Invoice deleted successfully");
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Error deleting invoice by ID", e);

			response.setMessage("Failed to delete invoice by ID");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
**/
}
