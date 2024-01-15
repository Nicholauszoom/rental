package com.dflex.ircs.portal.payment.api.controller;

import com.dflex.ircs.portal.invoice.api.dto.InvoiceValidationApiResBodyDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceValidationApiResDetailDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceValidationApiResDetailsDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceValidationApiResHeaderDto;
import com.dflex.ircs.portal.invoice.service.InvoiceItemService;
import com.dflex.ircs.portal.invoice.service.InvoiceService;
import com.dflex.ircs.portal.setup.dto.CommunicationApiDetailsDto;
import com.dflex.ircs.portal.setup.service.CommunicationApiService;
import com.dflex.ircs.portal.setup.service.OtherServiceInstitutionService;
import com.dflex.ircs.portal.setup.service.ServiceInstitutionService;
import com.dflex.ircs.portal.util.PKIUtils;
import com.dflex.ircs.portal.util.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentApiController {

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
    @PostMapping("/validation-v1")
    public ResponseEntity<String> receivePaymentValidationRequest(@RequestBody String requestContent,
                                                                  @RequestHeader Map<String, String> requestHeaders,
                                                                  HttpServletRequest request) {
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
        String transactionNumber = utils.getStringWithinXmlTagExclusive(requestContent, "transactionnumber");
        String requestIp = utils.getRequestIP(request);
        String requestProtocol = request.getProtocol();

        CommunicationApiDetailsDto commApi = null;
        InvoiceValidationApiResBodyDto invoiceValidationApiRes = null;
        InvoiceValidationApiResDetailsDto invoiceDetail = null;
        InvoiceValidationApiResHeaderDto invoiceValidationApiResHeader = null;
        List<InvoiceValidationApiResDetailDto> invoiceDetailList = new ArrayList<>();
        url = request.getRequestURI();

        Logger loggerPaymentValidation = LogManager.getLogger("PaymentValidation");
        loggerPaymentValidation.info("Event : Payment  Validation Api Request,EventId :"+processRequestId+",RequestId :"+requestId+""
                + ",ReceivedTime :"+receivedTime+"\nReceivedHeaders : "+requestHeaders+",\nAt Url :"+url+" ,RequestIp :"+requestIp+","
                + " RequestProtocol :"+requestProtocol+", \nReceivedContent :"+requestContent+"\n");

        try {
            if (requestHeaders.containsKey("clientkey") && requestHeaders.containsKey("clientcode")) {

                if (!utils.isNullOrEmpty(requestHeaders.get("clientcode")) && !utils.isNullOrEmpty(requestHeaders.get("clientkey"))) {

                    clientKey = requestHeaders.get("clientkey");
                    clientCode = requestHeaders.get("clientcode");

                    // Validate Incoming Request Schema
                    InputStream requestXsdSchema = getClass().getResourceAsStream("/xsd-templates/PaymentValidation.xsd");
                    Map<String, String> schemaValidationResult = utils.validateRequestXMLSchema(requestXsdSchema, requestContent);
                    if (schemaValidationResult.get("code").equals("1001")) {
                       // TO BE CONTINUE
                    }
                }
            }


            return ResponseEntity.ok("Request processed successfully");
        } catch (Exception e) {

            return ResponseEntity.status(500).body("Error processing the request");
        }
    }

}
