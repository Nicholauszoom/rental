package com.dflex.ircs.portal.setup.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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

import com.dflex.ircs.portal.setup.dto.ServiceTypeDto;
import com.dflex.ircs.portal.setup.service.ServiceTypeService;
import com.dflex.ircs.portal.util.Response;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/servicetype")
public class ServiceTypeController {

	@Autowired
    private ServiceTypeService serviceTypeService;
	
	@Autowired
	private MessageSource messageSource;
	
	Locale currentLocale = LocaleContextHolder.getLocale();
	
	String status = "";
	String message = "";
	Boolean error = false;

    protected Logger logger = LoggerFactory.getLogger(ServiceTypeController.class);

    @PostMapping("/list")
    public ResponseEntity<?> getServiceTypes(HttpServletRequest request) {
    	
    	List<ServiceTypeDto> serviceTypes = null;
    	try {
    		
    		
    		
    		message = messageSource.getMessage("message.1001",null, currentLocale);
			status = messageSource.getMessage("code.1001",null, currentLocale);
			error  = true;
			Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,error,message,serviceTypes,request.getRequestURI());
			return ResponseEntity.status(HttpStatus.OK).body(response);
    	} catch (Exception ex) {
    		
    		message = messageSource.getMessage("message.1004",null, currentLocale);
			status = messageSource.getMessage("code.1004",null, currentLocale);
			error  = true;
			Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,error,message,null,request.getRequestURI());
			return ResponseEntity.status(HttpStatus.OK).body(response);
    	}
        
    }
    
    @PostMapping("/create/servicetype")
    public ResponseEntity<?> createServiceType(@RequestBody ServiceTypeDto serviceType,JwtAuthenticationToken auth, HttpServletRequest request) {
    	
    	List<ServiceTypeDto> serviceTypes = null;
    	try {
    		
    		
    		
    		message = messageSource.getMessage("message.1001",null, currentLocale);
			status = messageSource.getMessage("code.1001",null, currentLocale);
			error  = true;
			Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,error,message,serviceTypes,request.getRequestURI());
			return ResponseEntity.status(HttpStatus.OK).body(response);
    	} catch (Exception ex) {
    		
    		message = messageSource.getMessage("message.1004",null, currentLocale);
			status = messageSource.getMessage("code.1004",null, currentLocale);
			error  = true;
			Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,error,message,null,request.getRequestURI());
			return ResponseEntity.status(HttpStatus.OK).body(response);
    	}
        
    }
    
    @PostMapping("/update/servicetype")
    public ResponseEntity<?> updateServiceType(@RequestBody ServiceTypeDto serviceType,JwtAuthenticationToken auth, HttpServletRequest request) {
    	
    	List<ServiceTypeDto> serviceTypes = null;
    	try {
    		
    		
    		
    		message = messageSource.getMessage("message.1001",null, currentLocale);
			status = messageSource.getMessage("code.1001",null, currentLocale);
			error  = true;
			Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,error,message,serviceTypes,request.getRequestURI());
			return ResponseEntity.status(HttpStatus.OK).body(response);
    	} catch (Exception ex) {
    		
    		message = messageSource.getMessage("message.1004",null, currentLocale);
			status = messageSource.getMessage("code.1004",null, currentLocale);
			error  = true;
			Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,error,message,null,request.getRequestURI());
			return ResponseEntity.status(HttpStatus.OK).body(response);
    	}
        
    }

}


