package com.dflex.ircs.portal.payment.api.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dflex.ircs.portal.data.entity.FormData1;
import com.dflex.ircs.portal.data.service.FormDataService;
import com.dflex.ircs.portal.payment.api.dto.PaymentDetailDto;
import com.dflex.ircs.portal.payment.entity.Payment;
import com.dflex.ircs.portal.payment.service.PaymentService;
import com.dflex.ircs.portal.util.Constants;
import com.dflex.ircs.portal.util.Response;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private FormDataService formDataService;
    
	@Autowired
	private MessageSource messageSource;

    Locale currentLocale = LocaleContextHolder.getLocale();
	
	String status = "";
	String message = "";
	Boolean error = false;

    protected Logger logger = LoggerFactory.getLogger(PaymentController.class);
    
    /**
     * Get Applied Costs
     * @param applicationUid
     * @param request
     * @return ResponseEntity
     */
    @PostMapping("/list/applicationuid/{applicationuid}")
    public ResponseEntity<?> applicationPaymentList(@PathVariable("applicationuid")String applicationUid,HttpServletRequest request) {

    	List<PaymentDetailDto> applicationPayments = new ArrayList<>();
        try {
        	
    		Optional<FormData1> application = formDataService.findFormData1ByApplicationUid(
    				UUID.fromString(applicationUid));
    		
    		List<Payment> payments = paymentService.findByApplicationNumberAndRecordStatusId(application.get().getApplicationNumber(),
    						Constants.RECORD_STATUS_ACTIVE);
    		if(payments != null && !payments.isEmpty()) {
        			
    			for(Payment payment : payments) {
    				
    				applicationPayments.add(new PaymentDetailDto(payment.getId(),
    						String.valueOf(payment.getPaymentUid()),payment.getTransactionNumber(),payment.getPaymentReference(),
    						payment.getInvoiceNumber(),payment.getPaymentNumber(),payment.getInvoicePaymentNumber(),payment.getPaidAmount(),payment.getCurrencyCode(),
    						payment.getCollectionAccountNumber(),payment.getTransactionDate(),payment.getReceivedDate(),payment.getPaymentMethod(),
    						payment.getPaymentMethodReference(),payment.getPayerName(),payment.getPayerPhoneNumber(),payment.getPayerEmail(),
    						payment.getPaymentFacilitatorCode(),payment.getPaymentFacilitator().getPaymentFacilitatorName()));
    			}
    			
        		message = messageSource.getMessage("message.1001", null, currentLocale);
				status = messageSource.getMessage("code.1001", null, currentLocale);
				error = false;
			} else {
				message = messageSource.getMessage("message.1007", null, currentLocale);
				status = messageSource.getMessage("code.1007", null, currentLocale);
				error = true;
			}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		message = messageSource.getMessage("message.1004",null, currentLocale);
			status = messageSource.getMessage("code.1004",null, currentLocale);
			error  = true;
    	}
        Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,error,message,applicationPayments,request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
