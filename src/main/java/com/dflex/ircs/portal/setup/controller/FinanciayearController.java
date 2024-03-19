package com.dflex.ircs.portal.setup.controller;


import com.dflex.ircs.portal.config.AuthDetailsDto;
import com.dflex.ircs.portal.setup.dto.FinancialDto;
import com.dflex.ircs.portal.setup.entity.FinancialYear;
import com.dflex.ircs.portal.setup.service.FinancialYearService;
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
@RequestMapping("/api/financial")
public class FinanciayearController {


    @Autowired
    private FinancialYearService financialYearService;

    @Autowired
    private Utils utils;

    @Autowired
    private MessageSource messageSource;

    Locale currentLocale = LocaleContextHolder.getLocale();

    String status = "";
    String message = "";
    Boolean error = false;

    protected Logger logger = LoggerFactory.getLogger(FinanciayearController.class);

    /**
     * Create New Financial year
     * @param financialDto
     * @return ResponseEntity
     */
    @PostMapping("/create")
    public ResponseEntity<?> createRevenueSourceEstimate(@RequestBody FinancialDto financialDto,
                                                         JwtAuthenticationToken auth, HttpServletRequest request) {

        FinancialYear financialYear = null;

        try {

            if(financialDto != null) {

                AuthDetailsDto authDetails = utils.getTokenAuthenticationDetails(auth);

                FinancialYear existingFinancialYear = financialYearService.findByFinancialYearIdAndRecordStatusId(
                        financialDto.getFinancialYearId(), Constants.RECORD_STATUS_ACTIVE);
                if (existingFinancialYear == null) {

                    FinancialYear newFinancialData = new FinancialYear(authDetails.getUserId(),authDetails.getUserName(),
                            financialDto.getFinancialYearUid(),financialDto.getShortYear(),financialDto.getYearStart(),
                            financialDto.getYearEnd(), Constants.RECORD_STATUS_ACTIVE);

                    financialYear = financialYearService.createFinancialYear(newFinancialData);

                    if (financialYear != null) {
                        message = messageSource.getMessage("general.create.success", new Object[] { "Revenue Source Estimate" },currentLocale);
                        status = messageSource.getMessage("code.1001", null, currentLocale);
                        error = false;
                    } else {
                        message = messageSource.getMessage("general.create.failure", new Object[] { "Revenue Source Estimate" },currentLocale);
                        status = messageSource.getMessage("code.1002", null, currentLocale);
                        error = true;
                    }
                } else {
                    financialYear = existingFinancialYear;
                    message = messageSource.getMessage("general.current.exists", new Object[] { "Revenue Source Estimate" }, currentLocale);
                    status = messageSource.getMessage("code.1069", null, currentLocale);
                    error = true;
                }
            } else {
                message = messageSource.getMessage("message.1005",null, currentLocale);
                status = messageSource.getMessage("code.1005", null, currentLocale);
                error = true;
            }
        } catch (Exception ex) {

            message = messageSource.getMessage("general.create.failure", new Object[] { "Revenue Source Estimate" }, currentLocale);
            status = messageSource.getMessage("code.1004", null, currentLocale);
            logger.error(message, ex);
            error = true;
        }

        Response response = new Response(String.valueOf(Calendar.getInstance().getTime()), status, error, message,financialYear, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
