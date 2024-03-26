package com.dflex.ircs.portal.module.controller;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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

import com.dflex.ircs.portal.module.entity.AppLookup;
import com.dflex.ircs.portal.module.service.AppLookupService;
import com.dflex.ircs.portal.util.Constants;
import com.dflex.ircs.portal.util.Response;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 
 * @author Augustino Mwageni
 * 
 * Api Controller class for Lookup Data
 *
 */
@RestController
@RequestMapping("/api/applookup")
public class AppLookupController {

	@Autowired
    private AppLookupService appLookupService;
	
	@Autowired
	private MessageSource messageSource;
	
	Locale currentLocale = LocaleContextHolder.getLocale();
	
	String status = "";
	String message = "";
	Boolean error = false;

    protected Logger logger = LoggerFactory.getLogger(AppLookupController.class);

    /**
     * Get lookup data by lookup type
     * @param lookupTypeId
     * @param request
     * @return ResponseEntity
     */
    @PostMapping("/list/lookuptype/{lookuptype}")
    public ResponseEntity<?> getApplicationLookupDataByLookupTypeId (@PathVariable("lookuptype") Long lookupTypeId,
    		HttpServletRequest request) {
    	
    	LinkedHashMap<String,Object> lookupData = new LinkedHashMap<>();
    	try {
    		
			List<AppLookup> appLookups = appLookupService.findByAppLookupTypeIdAndRecordStatusIdOrderByLookupKeyAsc(
					lookupTypeId,Constants.RECORD_STATUS_ACTIVE);
			if(appLookups != null && !appLookups.isEmpty()) {
				
				lookupData = appLookups.stream()
	            .collect(Collectors.toMap(AppLookup::getLookupKey, AppLookup::getLookupValue,
	            		(existing, replacement) -> existing, 
	                    LinkedHashMap::new ));
				
				message = messageSource.getMessage("message.1001",null, currentLocale);
    			status = messageSource.getMessage("code.1001",null, currentLocale);
    			error  = false;
			} else {
    			message = messageSource.getMessage("message.1007",null, currentLocale);
    			status = messageSource.getMessage("code.1007",null, currentLocale);
    			error  = true;
    		}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		message = messageSource.getMessage("message.1004",null, currentLocale);
			status = messageSource.getMessage("code.1004",null, currentLocale);
			error  = true;
    	}
    	Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,error,message,lookupData,request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}


