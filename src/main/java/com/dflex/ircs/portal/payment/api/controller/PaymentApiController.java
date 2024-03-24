package com.dflex.ircs.portal.payment.api.controller;

import java.io.InputStream;
import java.io.StringReader;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
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
import com.dflex.ircs.portal.invoice.entity.Invoice;
import com.dflex.ircs.portal.invoice.service.InvoiceService;
import com.dflex.ircs.portal.payment.api.dto.PaymentDetailDto;
import com.dflex.ircs.portal.payment.api.dto.PaymentDto;
import com.dflex.ircs.portal.payment.api.dto.PaymentHeaderDto;
import com.dflex.ircs.portal.payment.api.dto.PaymentValidationDto;
import com.dflex.ircs.portal.payment.entity.Payment;
import com.dflex.ircs.portal.payment.service.PaymentService;
import com.dflex.ircs.portal.setup.service.OtherServiceInstitutionService;
import com.dflex.ircs.portal.setup.service.ServiceInstitutionService;
import com.dflex.ircs.portal.util.Constants;
import com.dflex.ircs.portal.util.PKIUtils;
import com.dflex.ircs.portal.util.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

@RestController
@RequestMapping("/api/payment")
public class PaymentApiController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private PaymentService  paymentService;

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

    protected org.slf4j.Logger logger = LoggerFactory.getLogger(PaymentApiController.class);
    /**
     * payment  Validation Api for Portal Core Clients
     *
     * @param requestContent
     * @param requestHeaders
     * @param request
     * @return String
     */
    @PostMapping("/validation-v1")
    public PaymentDto receivePaymentValidationRequest(@RequestBody String requestContent,
                                                      @RequestHeader Map<String, String> requestHeaders,
                                                      HttpServletRequest request) {
        Logger logger = LoggerFactory.getLogger(PaymentApiController.class);
        Locale currentLocale = LocaleContextHolder.getLocale();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String receivedTime = utils.getLocalDateTime(new Date()).format(dateTimeFormatter);
        String processRequestId = utils.getToken();
        String responseStatusCode = "";
        String responseStatusDesc = "";
        String ackid = "";
        String remark = "";
        String url = "";
        Integer logLevel = 0;
        Long apiVersionNumber = 1L;

        String invoiceType = "";
        int detailCount = Integer.parseInt("");
        String clientKey = "";
        String clientCode = "";

        String requestId = utils.getStringWithinXmlTagExclusive(requestContent, "requestid");
        String paymentNumber = utils.getStringWithinXmlTagExclusive(requestContent, "paymentnumber");
        String transactionNumber = utils.getStringWithinXmlTagExclusive(requestContent, "transactionnumber");
        String requestIp = utils.getRequestIP(request);
        String requestProtocol = request.getProtocol();

        CommunicationApiDetailsDto commApi = null;

        PaymentHeaderDto paymentHeaderDTO = null;
        PaymentDetailDto paymentDetailDTO = null;
        List<PaymentDetailDto> paymentDetail = new ArrayList<>();
        url = request.getRequestURI();

        logger.info("Event : Payment  Validation Api Request,EventId :"+processRequestId+",RequestId :"+requestId+""
                + ",ReceivedTime :"+receivedTime+"\nReceivedHeaders : "+requestHeaders+",\nAt Url :"+url+" ,RequestIp :"+requestIp+","
                + " RequestProtocol :"+requestProtocol+", \nReceivedContent :"+requestContent+"\n");

        try {
            if (requestHeaders.containsKey("clientkey") && requestHeaders.containsKey("clientcode")) {

                if (!utils.isNullOrEmpty(requestHeaders.get("clientcode")) && !utils.isNullOrEmpty(requestHeaders.get("clientkey"))) {

                    clientKey = requestHeaders.get("clientkey");
                    clientCode = requestHeaders.get("clientcode");

                    // Validate Incoming Request Schema
                    InputStream requestXsdSchema = getClass().getResourceAsStream("/templates/xsd-templates/PaymentValidation.xsd");
                    Map<String, String> schemaValidationResult = utils.validateRequestXMLSchema(requestXsdSchema, requestContent);
                    if (schemaValidationResult.get("code").equals("1001")) {
                       // TO BE CONTINUE

                        JAXBContext jaxbContext = JAXBContext.newInstance(PaymentValidationDto.class);
                        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                        StringReader stringReader = new StringReader(requestContent);
                        PaymentValidationDto validationDTO = (PaymentValidationDto) unmarshaller.unmarshal(stringReader);

                        String requestMessage = utils.getStringWithinXml(requestContent, "payment");
                        String requestSignature = validationDTO.getHash();
                        PaymentDto paymentDetails = (PaymentDto) validationDTO.getPayment().getPaymentdtls();

                        logger.info("after reading from xml requestMessage {} ,the  signature {} , the details {}", requestMessage, requestSignature,paymentDetails);
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
                                                    Long sigAlg = commApi.getSignatureAlgo();

                                                    if (utils.isFileExist(certificateFile)){

                                                        if(!utils.isNullOrEmpty(certificatePassPhrase) && !utils.isNullOrEmpty(certificateAlias)) {

                                                            if (pkiUtils.verifySignature(requestSignature, requestMessage,certificatePassPhrase, certificateAlias,
                                                            		certificateFile,sigAlg)) {

                                                                List<Invoice> invoiceRequired = invoiceService.findByPaymentNumber(paymentDetails.getPaymenthdr().getPaymentnumber());
                                                                if(invoiceRequired != null && !invoiceRequired.isEmpty()) {
                                                                    for (Invoice invoice : invoiceRequired) {
                                                                        Payment payment = new Payment();
                                                                        payment.setInvoiceId(invoice.getId());
                                                                        payment.setInvoiceNumber(invoice.getInvoiceNumber());
                                                                        payment.setInvoicePaymentNumber(invoice.getPaymentNumber());
                                                                        payment.setPaymentNumber(paymentDetails.getPaymenthdr().getPaymentnumber());
                                                                        payment.setTransactionNumber(paymentDetails.getPaymentdtls().get(0).getTransactionnumber());
                                                                        payment.setPaymentReference(paymentDetailDTO.getTransactionreference());
                                                                        payment.setPayerName(paymentDetailDTO.getPayername());
                                                                        payment.setPayerPhoneNumber(paymentDetailDTO.getPayerphonenumber());
                                                                        payment.setPayerEmail(paymentDetailDTO.getPayeremail());
                                                                        payment.setPaidPmount(paymentDetailDTO.getPaymentamount());
                                                                        payment.setCurrencyCode(paymentDetailDTO.getCurrency());
                                                                        payment.setPaymentMethod(paymentDetailDTO.getPaymentmethod());

                                                                        // Save the payment to the database
                                                                        payment = paymentService.savePayment(payment);

                                                                    }

                                                                } else {
                                                                    responseStatusCode = Constants.INVOICE_NOTFOUND;
                                                                    logger.info("no  invoice found {} with code {}", invoiceRequired, responseStatusCode);

                                                                }
                                                            } else {

                                                                responseStatusCode = Constants.INVALID_CLIENT_KEY;
                                                                logger.info("no  invalid key found  with code {}",responseStatusCode);

                                                            }
                                                        } else {
                                                            responseStatusCode = Constants.INVALID_CLIENT_KEY;
                                                            logger.info("no  invalid key found  with code {}",responseStatusCode);                                                       }
                                                    } else {
                                                        responseStatusCode = Constants.INVALID_CLIENT_KEY;
                                                        logger.info("no  invalid key found  with code {}",responseStatusCode);

                                                    }
                                                } else {
                                                    responseStatusCode = Constants.INACTIVE_OR_DISABLED_CLIENT_CONFIGURATION;
                                                    logger.info("Client not  found  in the system  with code {}",responseStatusCode ,client);

                                                }
                                            } else {
                                                responseStatusCode = Constants.INACTIVE_OR_DISABLED_CLIENT_CONFIGURATION;
                                                logger.info("Client not  found  in the system  with code {}",responseStatusCode ,client);

                                            }
                                        } else {
                                            responseStatusCode = Constants.INACTIVE_OR_DISABLED_CLIENT_CONFIGURATION;
                                            logger.info("Client not  found  in the system  with code {}",responseStatusCode ,client);

                                        }
                                    } else {
                                        responseStatusCode = Constants.INACTIVE_OR_DISABLED_CLIENT_CONFIGURATION;
                                        logger.info("Client not  found  in the system  with code {}",responseStatusCode ,client);

                                    }
                                } else {
                                    responseStatusCode = Constants.INACTIVE_OR_DISABLED_CLIENT_CONFIGURATION;
                                    logger.info("Client not  found  in the system  with code {}",responseStatusCode ,client);

                                }
                            } else {
                                responseStatusCode = Constants.CLIENT_SYSTEM_CONFIGURATION_NOT_FOUND;
                                logger.info("Client not  found  in the system  with code {}",responseStatusCode ,client);

                            }
                        }catch (Exception e){
                            return null;
                        }

                    }
                    else {
                        logger.info("failed to validate the schema {}",schemaValidationResult);
                    }
                }
            }

            paymentHeaderDTO = new PaymentHeaderDto(ackid,paymentNumber, detailCount, requestId);

        } catch (Exception e) {
            paymentHeaderDTO = new PaymentHeaderDto(ackid,paymentNumber, detailCount, requestId);

        }
       PaymentDto paymentsDetails = new PaymentDto(paymentHeaderDTO, (List<PaymentDetailDto>) paymentDetailDTO);
        return paymentsDetails;
    }


    /**
     * return all the invoice
     *
     * @return String
     */


//    @GetMapping(value = "/paymentAll")
//    public ResponseEntity<Response<List<PaymentDTO>>> getAllPayments() {
//        Response<List<PaymentDTO>> response = new Response<>();
//        try {
//            List<Payment> payments = paymentService.findAll();
//
//            List<PaymentDTO> paymentDTOs = payments.stream()
//                    .map(payment -> new PaymentDTO(/* pass relevant fields from 'invoice' */))
//                    .collect(Collectors.toList());
//            response.setData(paymentDTOs);
//            response.setCode("200");
//            response.setMessage("All payments retrieved successfully");
//
//            return new ResponseEntity<>(response, HttpStatus.OK);
//
//        } catch (Exception e) {
//            logger.error("Error retrieving all payments", e);
//            response.setCode("500");
//            response.setMessage("Failed to retrieve all payments");
//            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


//    @GetMapping("/paymentById/{id}")
//    public ResponseEntity<Response<PaymentDTO>> getPaymentById(@PathVariable Long id) {
//        Response<PaymentDTO> response = new Response<>();
//        try {
//            Optional<Payment> payment = paymentService.findById(id);
//            if (payment == null) {
//                response.setCode("404");
//                response.setMessage("Payment not found");
//                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//            }
//            response.setData(payment);
//            response.setCode("200");
//            response.setMessage("Payment retrieved successfully");
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            logger.error("Error retrieving payment by id", e);
//            response.setCode("500");
//            response.setMessage("Failed to retrieve payment by id");
//            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


}
