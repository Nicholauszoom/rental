package com.dflex.ircs.portal.setup.controller;

import com.dflex.ircs.portal.config.AuthDetailsDto;
import com.dflex.ircs.portal.setup.dto.CurrencyDto;
import com.dflex.ircs.portal.setup.dto.PaymentFacilitatorDto;
import com.dflex.ircs.portal.setup.entity.Currency;
import com.dflex.ircs.portal.setup.entity.PaymentFacilitator;
import com.dflex.ircs.portal.setup.service.CurrencyService;
import com.dflex.ircs.portal.setup.service.ServiceTypeService;
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

import java.util.Calendar;
import java.util.Locale;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {


    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private Utils utils;

    @Autowired
    private MessageSource messageSource;

    Locale currentLocale = LocaleContextHolder.getLocale();

    String status = "";
    String message = "";
    Boolean error = false;

    protected Logger logger = LoggerFactory.getLogger(CurrencyController.class);

    /**
     * Create   Currency
     * @param request
     * @return ResponseEntity
     */


    @PostMapping("/create")
    public ResponseEntity<?> createCurrency(@RequestBody CurrencyDto currencyDto , JwtAuthenticationToken auth,
                                            HttpServletRequest request) {

        CurrencyDto  currencyValue = null;

        try {
            if(currencyDto != null){
                AuthDetailsDto authDetails = utils.getTokenAuthenticationDetails(auth);
                Currency currency = currencyService.findBycurrencyCodeAndRecordStatusId(currencyDto.getCurrencyCode(),Constants.RECORD_STATUS_ACTIVE);
                if(currency == null) {

                    Currency addCurrency = new Currency(authDetails.getUserId(), authDetails.getUserName(), currencyDto.getCurrencyCode(),
                            currencyDto.getCurrencyName(),currencyDto.getRecordStatusId(),currencyDto.getCurrencyUid());

                    Currency newCurrency = currencyService.saveCurrency(addCurrency);
                    if (newCurrency != null) {
                        currencyValue = new CurrencyDto(newCurrency.getId(),newCurrency.getCurrencyUid(), newCurrency.getCurrencyCode(),
                                newCurrency.getCurrencyName(), newCurrency.getRecordStatusId());

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
        Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,error,message,currencyValue,request.getRequestURI());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
