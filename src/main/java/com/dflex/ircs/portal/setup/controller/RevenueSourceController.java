package com.dflex.ircs.portal.setup.controller;

import com.dflex.ircs.portal.revenue.api.dto.RevenueSourceDTO;
import com.dflex.ircs.portal.setup.dto.RevenueSourceDetailsDto;
import com.dflex.ircs.portal.setup.service.RevenueSourceService;
import com.dflex.ircs.portal.util.Constants;
import com.dflex.ircs.portal.util.Response;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/revenuesource")
public class RevenueSourceController {

	@Autowired
    private RevenueSourceService revenueSourceService;
	
	@Autowired
	private MessageSource messageSource;
	
	Locale currentLocale = LocaleContextHolder.getLocale();
	
	String status = "";
	String message = "";
	Boolean error = false;

    protected Logger logger = LoggerFactory.getLogger(RevenueSourceController.class);

    @PostMapping("/institutioncode/{institutioncode}/module/{moduleid}")
    public ResponseEntity<?> getInstitutionRevenueSourcesByInstitutionCodeAndModuleId(
    		@PathVariable("institutioncode") String institutionCode,@PathVariable("moduleid") Long moduleId,
    		HttpServletRequest request) {

        List<RevenueSourceDetailsDto> revenueSources = revenueSourceService.findDetailsByServiceInstitutionCodeAndAppModuleIdAndRecordStatusId(
        		institutionCode, moduleId, Constants.RECORD_STATUS_ACTIVE);
		if(revenueSources != null && !revenueSources.isEmpty()) {
			
			message = messageSource.getMessage("message.1001",null, currentLocale);
			status = messageSource.getMessage("code.1001",null, currentLocale);
			error  = false;
			Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,error,message,revenueSources,request.getRequestURI());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			
			message = messageSource.getMessage("message.1009",null, currentLocale);
			status = messageSource.getMessage("code.1009",null, currentLocale);
			error  = true;
			Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,error,message,null,request.getRequestURI());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
    }

}


