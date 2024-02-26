package com.dflex.ircs.portal.setup.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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

import com.dflex.ircs.portal.config.AuthDetailsDto;
import com.dflex.ircs.portal.setup.dto.ServiceTypeDto;
import com.dflex.ircs.portal.setup.entity.ServiceType;
import com.dflex.ircs.portal.setup.service.ServiceTypeService;
import com.dflex.ircs.portal.util.Constants;
import com.dflex.ircs.portal.util.Response;
import com.dflex.ircs.portal.util.Utils;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 
 * Api Controller class for ServiceType
 *
 */

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

    /**
     * List  Service Types
     * @param request
     * @return ResponseEntity
     */
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
					serviceTypeDto.setParentServiceTypeId(service.getParentServiceType().getId());
					serviceTypeDto.setServiceTypeLevel(service.getServiceTypeLevel());
					serviceTypeDto.setRecordStatusId(service.getRecordStatusId());

					serviceTypes.add(serviceTypeDto);
					
				}
				message = messageSource.getMessage("message.1001", null, currentLocale);
				status = messageSource.getMessage("code.1001", null, currentLocale);
				error = false;
			} else {
				message = messageSource.getMessage("message.1007", null, currentLocale);
				status = messageSource.getMessage("code.1007", null, currentLocale);
				error = true;
			}
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
    
    /**
     * Create Service Type
     * @param serviceTypeDto
     * @param auth
     * @param request
     * @return ResponseEntity
     */
    @PostMapping("/create/servicetype")
    public ResponseEntity<?> createServiceType(@RequestBody ServiceTypeDto serviceTypeDto,JwtAuthenticationToken auth,
    		HttpServletRequest request) {

    	ServiceTypeDto service = null;
    	
    	try {
			if(serviceTypeDto != null){
				
				AuthDetailsDto authDetails = utils.getTokenAuthenticationDetails(auth);
				ServiceType oldServiceType = serviceTypeService.findByServiceTypeCodeAndRecordStatusId(serviceTypeDto.getServiceTypeCode(),
						Constants.RECORD_STATUS_ACTIVE);
				if(oldServiceType == null && ((serviceTypeDto.getServiceTypeLevel().equals(Constants.SERVICE_TYPE_LEVEL_2)
						&& serviceTypeDto.getParentServiceTypeId() != null)
						|| serviceTypeDto.getServiceTypeLevel().equals(Constants.SERVICE_TYPE_LEVEL_1))) {
					
					Optional<ServiceType> parentServiceType = serviceTypeService.findById(serviceTypeDto.getParentServiceTypeId());
					if (parentServiceType.isPresent() || serviceTypeDto.getServiceTypeLevel().equals(Constants.SERVICE_TYPE_LEVEL_1)) {
						
						ServiceType serviceType = new ServiceType( authDetails.getUserId(), authDetails.getUserName(), serviceTypeDto.getServiceTypeCode(),
								serviceTypeDto.getServiceTypeLevel(), serviceTypeDto.getServiceTypeName(),serviceTypeDto.getRecordStatusId(),
								parentServiceType.get());
						
						ServiceType newServiceType = serviceTypeService.saveServiceType(serviceType);
						if(newServiceType != null) {
							
							if(newServiceType.getServiceTypeLevel().equals(Constants.SERVICE_TYPE_LEVEL_1)) {
								newServiceType.setParentServiceType(newServiceType);
								newServiceType = serviceTypeService.saveServiceType(newServiceType);
							}
							
							service = new ServiceTypeDto(newServiceType.getId(),String.valueOf(newServiceType.getServiceTypeUid()),
									newServiceType.getServiceTypeCode(),newServiceType.getServiceTypeName(),
									newServiceType.getParentServiceType().getId(),newServiceType.getServiceTypeLevel(),
									newServiceType.getRecordStatusId());
							
							message = messageSource.getMessage("general.create.success", new Object[] { "Service Type" },currentLocale);
							status = messageSource.getMessage("code.1001", null, currentLocale);
							error = false;
						} else {
							message = messageSource.getMessage("general.create.failure", new Object[] { "Service Type" },currentLocale);
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
			} else {
				message = messageSource.getMessage("message.1005",null, currentLocale);
				status = messageSource.getMessage("code.1005", null, currentLocale);
				error = true;
			}
    	} catch (Exception ex) {
    		message = messageSource.getMessage("message.1004",null, currentLocale);
			status = messageSource.getMessage("code.1004",null, currentLocale);
			error  = true;
    	}
    	Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,error,message,service,request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    /**
     * Update Service Type
     * @param serviceType
     * @param auth
     * @param request
     * @return ResponseEntity
     */
    @PostMapping("/update/servicetype")
    public ResponseEntity<?> updateServiceType(@RequestBody ServiceTypeDto serviceTypeDto,JwtAuthenticationToken auth,
    		HttpServletRequest request) {
    	
    	ServiceTypeDto service = null;
    	try {
    		
    		if(serviceTypeDto != null){
				
    			Optional<ServiceType> existingServiceType = serviceTypeService.findById(serviceTypeDto.getId());
				AuthDetailsDto authDetails = utils.getTokenAuthenticationDetails(auth);
				
				if(existingServiceType.isPresent()){
					
					ServiceType otherServiceType = serviceTypeService.findByServiceTypeCodeAndRecordStatusId(serviceTypeDto.getServiceTypeCode()
							, Constants.RECORD_STATUS_ACTIVE);
					if(otherServiceType == null || otherServiceType.getId().equals(existingServiceType.get().getId())) {
						
						existingServiceType.get().setServiceTypeCode(serviceTypeDto.getServiceTypeCode());
						existingServiceType.get().setServiceTypeName(serviceTypeDto.getServiceTypeName());
						existingServiceType.get().setRecordStatusId(serviceTypeDto.getRecordStatusId());
						existingServiceType.get().setUpdatedBy(authDetails.getUserId());
						existingServiceType.get().setUpdatedByUserName(authDetails.getUserName());
						if(!existingServiceType.get().getParentServiceType().getId().equals(serviceTypeDto.getId())) {
							existingServiceType.get().setParentServiceType(serviceTypeService.findById(serviceTypeDto.getId()).get());
						}
						ServiceType updatedServiceType = serviceTypeService.saveServiceType(existingServiceType.get());
						if(updatedServiceType != null) {
							
							service = new ServiceTypeDto(updatedServiceType.getId(),String.valueOf(updatedServiceType.getServiceTypeUid()),
									updatedServiceType.getServiceTypeCode(),updatedServiceType.getServiceTypeName(),
									updatedServiceType.getParentServiceType().getId(),updatedServiceType.getServiceTypeLevel(),
									updatedServiceType.getRecordStatusId());
							
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
		Response response = new Response(String.valueOf(Calendar.getInstance().getTime()), status, error, message, service, request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}


