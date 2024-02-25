package com.dflex.ircs.portal.setup.controller;

import java.util.*;

import com.dflex.ircs.portal.config.AuthDetailsDto;
import com.dflex.ircs.portal.setup.entity.RevenueSourceEstimate;
import com.dflex.ircs.portal.setup.entity.ServiceType;
import com.dflex.ircs.portal.util.Constants;
import com.dflex.ircs.portal.util.Utils;
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

import static org.springframework.util.ClassUtils.isPresent;

@RestController
@RequestMapping("/api/servicetype")
public class ServiceTypeController {

	@Autowired
    private ServiceTypeService serviceTypeService;

	@Autowired
	private Utils utils;
	
	@Autowired
	private MessageSource messageSource;
	
	Locale currentLocale = LocaleContextHolder.getLocale();
	
	String status = "";
	String message = "";
	Boolean error = false;

    protected Logger logger = LoggerFactory.getLogger(ServiceTypeController.class);

    @PostMapping("/list")
    public ResponseEntity<?> getServiceTypes(HttpServletRequest request) {

		List<ServiceTypeDto> serviceTypes = new ArrayList<>();
		try {
			List<ServiceType> serviceTypeData = serviceTypeService.findAll();
			if(serviceTypeData != null){
				for (ServiceType service : serviceTypeData) {
					ServiceTypeDto serviceTypeDto = new ServiceTypeDto();
					serviceTypeDto.setId(service.getId());
					serviceTypeDto.setServiceTypeUid(service.getServiceTypeUid().toString());
					serviceTypeDto.setServiceTypeCode(service.getServiceTypeCode());
					serviceTypeDto.setServiceTypeName(service.getServiceTypeName());
					serviceTypeDto.setParent_service_type_id(service.getParentServiceType().getId());
					serviceTypeDto.setServiceTypeLevel(service.getServiceTypeLevel());
					serviceTypeDto.setRecordStatusId(service.getRecordStatusId());

					serviceTypes.add(serviceTypeDto);
				}
			}else {
				message = messageSource.getMessage("message.1006", null, currentLocale);
				status = messageSource.getMessage("code.1006", null, currentLocale);
				error = true;
			}
			message = messageSource.getMessage("message.1001", null, currentLocale);
			status = messageSource.getMessage("code.1001", null, currentLocale);
			error = false;
			Response response = new Response(String.valueOf(Calendar.getInstance().getTime()), status, error, message, serviceTypes, request.getRequestURI());
			return ResponseEntity.status(HttpStatus.OK).body(response);

		} catch (Exception e) {
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
			if(serviceType != null){
				AuthDetailsDto authDetails = utils.getTokenAuthenticationDetails(auth);
				Optional<ServiceType> parentServiceType = serviceTypeService.findById(serviceType.getParent_service_type_id());
				if (parentServiceType.isPresent()) {
					ServiceType parentServiceTypeId = parentServiceType.get();
					ServiceType newServiceType = new ServiceType( authDetails.getUserId(), authDetails.getUserName(), serviceType.getServiceTypeCode(), serviceType.getServiceTypeLevel(), serviceType.getServiceTypeUid(),
							serviceType.getServiceTypeName(),
							serviceType.getRecordStatusId(),
							parentServiceTypeId
					);
					serviceTypes = (List<ServiceTypeDto>) serviceTypeService.saveServiceType(newServiceType);
					if(serviceTypes != null){
						message = messageSource.getMessage("general.create.success", new Object[] { "Revenue Source Estimate" },currentLocale);
						status = messageSource.getMessage("code.1001", null, currentLocale);
						error = false;
						Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,error,message,serviceTypes,request.getRequestURI());
						return ResponseEntity.status(HttpStatus.OK).body(response);
					}else {
						message = messageSource.getMessage("general.create.failure", new Object[] { "Revenue Source Estimate" },currentLocale);
						status = messageSource.getMessage("code.1002", null, currentLocale);
						error = true;
					}
				} else {
					message = messageSource.getMessage("message.1005",null, currentLocale);
					status = messageSource.getMessage("code.1005", null, currentLocale);
					error = true;
				}
			}else {
				message = messageSource.getMessage("message.1005",null, currentLocale);
				status = messageSource.getMessage("code.1005", null, currentLocale);
				error = true;
			}
			Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,error,message,null,request.getRequestURI());
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
    	try {
    		if(serviceType != null){
				Optional<ServiceType> service = serviceTypeService.findById(serviceType.getId());
				AuthDetailsDto authDetails = utils.getTokenAuthenticationDetails(auth);
				if(service.isPresent()){
					ServiceType existingServiceType = service.get();
					existingServiceType.setServiceTypeUid(UUID.fromString(serviceType.getServiceTypeUid()));
					existingServiceType.setServiceTypeCode(serviceType.getServiceTypeCode());
					existingServiceType.setServiceTypeName(serviceType.getServiceTypeName());
					existingServiceType.setServiceTypeLevel(serviceType.getServiceTypeLevel());
					existingServiceType.setRecordStatusId(serviceType.getRecordStatusId());
					authDetails.getUserName();
					authDetails.getUserId();
					ServiceType updatedServiceType = serviceTypeService.saveServiceType(existingServiceType);


					message = messageSource.getMessage("message.1001",null, currentLocale);
					status = messageSource.getMessage("code.1001",null, currentLocale);
					error  = true;
					Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,error,message,updatedServiceType,request.getRequestURI());
					return ResponseEntity.status(HttpStatus.OK).body(response);
				}else{
					message = messageSource.getMessage("message.1007",null, currentLocale);
					status = messageSource.getMessage("code.1007",null, currentLocale);
					error  = true;
				}
			}else {
				message = messageSource.getMessage("message.1005",null, currentLocale);
				status = messageSource.getMessage("code.1005",null, currentLocale);
				error  = true;
			}

    	} catch (Exception ex) {
    		message = messageSource.getMessage("message.1004",null, currentLocale);
			status = messageSource.getMessage("code.1004",null, currentLocale);
			error  = true;
			Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,error,message,null,request.getRequestURI());
			return ResponseEntity.status(HttpStatus.OK).body(response);
    	}
		Response response = new Response(String.valueOf(Calendar.getInstance().getTime()), status, error, message, null, request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}


