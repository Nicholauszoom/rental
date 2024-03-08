package com.dflex.ircs.portal.setup.controller;

import com.dflex.ircs.portal.config.AuthDetailsDto;
import com.dflex.ircs.portal.setup.dto.PaymentFacilitatorDto;
import com.dflex.ircs.portal.setup.entity.PaymentFacilitator;
import com.dflex.ircs.portal.setup.service.PaymentFacilitatorService;
import com.dflex.ircs.portal.util.Constants;
import com.dflex.ircs.portal.util.Response;
import com.dflex.ircs.portal.util.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 *
 * Api Controller class for ServiceType
 *
 */

@RestController
@RequestMapping("/api/paymentFacilitator")
public class PaymentFacilitatorController {

    @Autowired
    PaymentFacilitatorService facilitatorService;


    @Autowired
    private Utils utils;

    @Autowired
    private MessageSource messageSource;

    Locale currentLocale = LocaleContextHolder.getLocale();

    String status = "";
    String message = "";
    Boolean error = false;

    protected Logger logger = LoggerFactory.getLogger(PaymentFacilitatorController.class);

    /**
     * List  Payments
     * @param request
     * @return ResponseEntity
     */
    @PostMapping("/list")
    public ResponseEntity<?> getPaymentFacilitator(HttpServletRequest request) {

        List<PaymentFacilitatorDto>  facilitatorList = new ArrayList<>();
        try {
            List<PaymentFacilitator> paymentFacilitatorsData = facilitatorService.findAll();
            if(paymentFacilitatorsData != null){
                for (PaymentFacilitator facilitator : paymentFacilitatorsData) {
                    PaymentFacilitatorDto paymentFacilitatorDto = new PaymentFacilitatorDto();
                    paymentFacilitatorDto.setId(facilitator.getId());
                    paymentFacilitatorDto.setPaymentFacilitatorCode(facilitator.getPaymentFacilitatorCode());
                    paymentFacilitatorDto.setPaymentFacilitatorName(facilitator.getPaymentFacilitatorName());
                    paymentFacilitatorDto.setPaymentFacilitatorShortName(facilitator.getPaymentFacilitatorShortName());
                    paymentFacilitatorDto.setRecordStatusId(facilitator.getRecordStatusId());

                    facilitatorList.add(paymentFacilitatorDto);

                }
                message = messageSource.getMessage("message.1001", null, currentLocale);
                status = messageSource.getMessage("code.1001", null, currentLocale);
                error = false;
            } else {
                message = messageSource.getMessage("message.1007", null, currentLocale);
                status = messageSource.getMessage("code.1007", null, currentLocale);
                error = true;
            }
            Response response = new Response(String.valueOf(Calendar.getInstance().getTime()), status, error, message, facilitatorList, request.getRequestURI());
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            message = messageSource.getMessage("message.1004",null, currentLocale);
            status = messageSource.getMessage("code.1004",null, currentLocale);
            error  = true;
            Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,error,message,null,request.getRequestURI());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    /**
     * Create Payments
     * @param paymentFacilitator Dto
     * @param auth
     * @param request
     * @return ResponseEntity
     */
    @PostMapping("/create/paymentFacilitator")
    public ResponseEntity<?> createPaymentFacilitator(@RequestBody PaymentFacilitatorDto  paymentFacilitator , JwtAuthenticationToken auth,
                                                      HttpServletRequest request) {

        PaymentFacilitatorDto paymentFacilitatorDto = null;

        try {
            if(paymentFacilitator != null){

                AuthDetailsDto authDetails = utils.getTokenAuthenticationDetails(auth);
                PaymentFacilitator facilitator = facilitatorService.findByPaymentFacilitatorCodeAndRecordStatusId(paymentFacilitator.getPaymentFacilitatorCode(),Constants.RECORD_STATUS_ACTIVE);
                if(facilitator == null) {

                    PaymentFacilitator addPaymentFacilitator = new PaymentFacilitator(authDetails.getUserId(), authDetails.getUserName(), paymentFacilitator.getPaymentFacilitatorName(),
                            paymentFacilitator.getPaymentFacilitatorShortName(),paymentFacilitator.getPaymentFacilitatorCode(), paymentFacilitator.getRecordStatusId());

                    PaymentFacilitator newPaymentFacilitator = facilitatorService.savePaymentFacilitator(addPaymentFacilitator);
                    if (newPaymentFacilitator != null) {
                        paymentFacilitatorDto = new PaymentFacilitatorDto(newPaymentFacilitator.getId(), String.valueOf(newPaymentFacilitator.getPaymentFacilitatorUid()), newPaymentFacilitator.getPaymentFacilitatorName(),
                                newPaymentFacilitator.getPaymentFacilitatorShortName(), newPaymentFacilitator.getPaymentFacilitatorCode(), newPaymentFacilitator.getRecordStatusId());

                        message = messageSource.getMessage("general.create.success", new Object[] { "Payment Facilitator" },currentLocale);
                        status = messageSource.getMessage("code.1001", null, currentLocale);
                        error = false;
                    } else {
                        message = messageSource.getMessage("general.create.failure", new Object[] { "Payment Facilitator" },currentLocale);
                        status = messageSource.getMessage("code.1002", null, currentLocale);
                        error = true;
                    }
                } else {
                    message = messageSource.getMessage("message.1005",null, currentLocale);
                    status = messageSource.getMessage("code.1005", null, currentLocale);
                    error = true;
                }
            } else {
                message = messageSource.getMessage("message.1005",null, currentLocale);
                status = messageSource.getMessage("code.1005", null, currentLocale);
                error = true;
            }


        }catch (Exception ex){
            message = messageSource.getMessage("message.1004",null, currentLocale);
            status = messageSource.getMessage("code.1004",null, currentLocale);
            error  = true;
        }
        Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,error,message,paymentFacilitatorDto,request.getRequestURI());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Update  Payment
     * @param paymentFacilitator
     * @param auth
     * @param request
     * @return ResponseEntity
     */
    @PostMapping("/update/servicetype")
    public ResponseEntity<?> updateServiceType(@RequestBody PaymentFacilitatorDto paymentFacilitatorDto,JwtAuthenticationToken auth,
                                               HttpServletRequest request) {
        PaymentFacilitatorDto facilitator = null;
        try {

            if(paymentFacilitatorDto != null){

                Optional<PaymentFacilitator> presentFacilitator = facilitatorService.findById(paymentFacilitatorDto.getId());
                AuthDetailsDto authDetails = utils.getTokenAuthenticationDetails(auth);

                if(presentFacilitator.isPresent()){

                    PaymentFacilitator otherFacilitator = facilitatorService.findByPaymentFacilitatorCodeAndRecordStatusId(paymentFacilitatorDto.getPaymentFacilitatorCode(),
                            , Constants.RECORD_STATUS_ACTIVE);
                    if(otherFacilitator == null || otherFacilitator.getId().equals(presentFacilitator.get().getId())) {

                        presentFacilitator.get().setPaymentFacilitatorCode(paymentFacilitatorDto.getPaymentFacilitatorCode());
                        presentFacilitator.get().setPaymentFacilitatorName(paymentFacilitatorDto.getPaymentFacilitatorName());
                        presentFacilitator.get().setPaymentFacilitatorShortName(paymentFacilitatorDto.getPaymentFacilitatorShortName());
                        presentFacilitator.get().setRecordStatusId(paymentFacilitatorDto.getRecordStatusId());
                        presentFacilitator.get().setUpdatedBy(authDetails.getUserId());
                        presentFacilitator.get().setUpdatedByUserName(authDetails.getUserName());


                        PaymentFacilitator updatedPaymentFacilitator = facilitatorService.savePaymentFacilitator(presentFacilitator.get());
                        if(updatedPaymentFacilitator != null) {

                            facilitator = new PaymentFacilitatorDto(updatedPaymentFacilitator.getId(),String.valueOf(updatedPaymentFacilitator.getPaymentFacilitatorUid()),
                                    updatedPaymentFacilitator.getPaymentFacilitatorName(),updatedPaymentFacilitator.getPaymentFacilitatorName(),updatedPaymentFacilitator.getPaymentFacilitatorCode(),
                                    updatedPaymentFacilitator.getRecordStatusId());

                            message = messageSource.getMessage("general.update.success", new Object[] { "Service Type" },currentLocale);
                            status = messageSource.getMessage("code.1001", null, currentLocale);
                            error = false;

                        } else {
                            message = messageSource.getMessage("general.update.failure", new Object[] { "Service Type" },currentLocale);
                            status = messageSource.getMessage("code.1001", null, currentLocale);
                            error = true;
                        }
                    } else {
                        message = messageSource.getMessage("message.1005",null, currentLocale);
                        status = messageSource.getMessage("code.1005",null, currentLocale);
                        error  = true;
                    }
                } else {
                    message = messageSource.getMessage("message.1007",null, currentLocale);
                    status = messageSource.getMessage("code.1007",null, currentLocale);
                    error  = true;
                }
            } else {
                message = messageSource.getMessage("message.1005",null, currentLocale);
                status = messageSource.getMessage("code.1005",null, currentLocale);
                error  = true;
            }
        } catch (Exception ex) {
            message = messageSource.getMessage("message.1004",null, currentLocale);
            status = messageSource.getMessage("code.1004",null, currentLocale);
            error  = true;
        }
        Response response = new Response(String.valueOf(Calendar.getInstance().getTime()), status, error, message, facilitator, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
