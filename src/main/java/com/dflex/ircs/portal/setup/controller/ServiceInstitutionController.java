package com.dflex.ircs.portal.setup.controller;

import com.dflex.ircs.portal.config.AuthDetailsDto;
import com.dflex.ircs.portal.setup.dto.ServiceInstitutionDto;
import com.dflex.ircs.portal.setup.entity.ServiceInstitution;
import com.dflex.ircs.portal.setup.entity.ServiceInstitutionCategory;
import com.dflex.ircs.portal.setup.service.ServiceInstitutionCategoryService;
import com.dflex.ircs.portal.setup.service.ServiceInstitutionService;
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

@RestController
@RequestMapping("/api/serviceInstitutions")
public class ServiceInstitutionController {
    @Autowired
    private ServiceInstitutionService serviceInstitutionService;

    @Autowired
    private ServiceInstitutionCategoryService  serviceInstitutionCategoryService;

    @Autowired
    private Utils utils;

    @Autowired
    private MessageSource messageSource;
    Locale currentLocale = LocaleContextHolder.getLocale();

    String status = "";
    String message = "";
    Boolean error = false;

    protected Logger logger = LoggerFactory.getLogger(ServiceInstitutionController.class);

    /**
     * List  Service Institutions
     * @param request
     * @return ResponseEntity
     */

    @PostMapping("/list")
    public ResponseEntity<?> getServiceTypes(HttpServletRequest request) {
        List<ServiceInstitutionDto> serviceInstitutionDtoList = new ArrayList<>();
        try {
            List<ServiceInstitution> serviceData = serviceInstitutionService.findAll();
            if(serviceData != null){
                for (ServiceInstitution service : serviceData) {
                    ServiceInstitutionDto serviceInstitutionDto = new ServiceInstitutionDto();
                    serviceInstitutionDto.setId(service.getId());
                    serviceInstitutionDto.setInstitutionCode(service.getInstitutionCode());
                    serviceInstitutionDto.setInstitutionName(service.getInstitutionName());
                    serviceInstitutionDto.setInstitutionNumber(service.getInstitutionNumber());
                    serviceInstitutionDto.setEmail(service.getEmail());
                    serviceInstitutionDto.setPostalAddress(service.getPostalAddress());
                    serviceInstitutionDto.setPrimaryPhoneNumber(service.getPrimaryPhoneNumber());
                    serviceInstitutionDto.setSecondaryPhoneNumber(service.getSecondaryPhoneNumber());
                    serviceInstitutionDto.setServiceInstitutionCategoryId(serviceInstitutionDto.getServiceInstitutionCategoryId());
                    serviceInstitutionDto.setRecordStatusId(service.getRecordStatusId());

                    serviceInstitutionDtoList.add(serviceInstitutionDto);

                }
                message = messageSource.getMessage("message.1001", null, currentLocale);
                status = messageSource.getMessage("code.1001", null, currentLocale);
                error = false;
            } else {
                message = messageSource.getMessage("message.1007", null, currentLocale);
                status = messageSource.getMessage("code.1007", null, currentLocale);
                error = true;
            }
            Response response = new Response(String.valueOf(Calendar.getInstance().getTime()), status, error, message, serviceInstitutionDtoList, request.getRequestURI());
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
     * Create Service Institutions controller
//     * @param serviceInstutionDto
     * @param auth
     * @param request
     * @return ResponseEntity
     */
    @PostMapping("/create/servicetype")
    public ResponseEntity<?> createServiceType(@RequestBody ServiceInstitutionDto serviceInstitutionDto, JwtAuthenticationToken auth,
                                               HttpServletRequest request) {

        ServiceInstitutionDto serviceInstitution = null;

        try {
            if(serviceInstitutionDto != null){

                AuthDetailsDto authDetails = utils.getTokenAuthenticationDetails(auth);
                ServiceInstitution getPreviousInstitutions = serviceInstitutionService.findByInstitutionCodeAndRecordStatusId(serviceInstitutionDto.getInstitutionCode(),
                        Constants.RECORD_STATUS_ACTIVE);
                if(getPreviousInstitutions == null) {
                    Optional<ServiceInstitutionCategory> serviceInstitutionCategory = serviceInstitutionCategoryService.findById(serviceInstitutionDto.getServiceInstitutionCategoryId());
                    if (serviceInstitutionCategory.isPresent()) {

                        ServiceInstitution institution = new ServiceInstitution(authDetails.getUserId(), authDetails.getUserName(), serviceInstitutionDto.getInstitutionCode(),
                                serviceInstitutionDto.getInstitutionNumber(), serviceInstitutionDto.getPostalAddress(), serviceInstitutionDto.getPhysicalAddress(), serviceInstitutionDto.getInstitutionName(),
                                serviceInstitutionDto.getSecondaryPhoneNumber(), serviceInstitutionDto.getPrimaryPhoneNumber(), serviceInstitutionDto.getEmail(), serviceInstitutionCategory.get().getId(),
                                serviceInstitutionDto.getRecordStatusId()
                        );

                        ServiceInstitution newServiceInstitution = serviceInstitutionService.saveServiceInstitution(institution);
                        if (newServiceInstitution != null) {
                                   serviceInstitution = new ServiceInstitutionDto(newServiceInstitution.getId(), String.valueOf(newServiceInstitution.getServiceInstitutionUid()), newServiceInstitution.getInstitutionCode(),
                                    newServiceInstitution.getPhysicalAddress(), newServiceInstitution.getPostalAddress(), newServiceInstitution.getEmail(),
                                    newServiceInstitution.getInstitutionNumber(), newServiceInstitution.getInstitutionName(), newServiceInstitution.getPrimaryPhoneNumber(), newServiceInstitution.getSecondaryPhoneNumber(),
                                    newServiceInstitution.getRecordStatusId(), newServiceInstitution.getServiceInstitutionCategory().getId()
                            );

                            message = messageSource.getMessage("general.create.success", new Object[] { "Service Institutions" },currentLocale);
                            status = messageSource.getMessage("code.1001", null, currentLocale);
                            error = false;
                        } else {
                            message = messageSource.getMessage("general.create.failure", new Object[] { "Service Institutions" },currentLocale);
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


        }catch (Exception ex){
            message = messageSource.getMessage("message.1004",null, currentLocale);
            status = messageSource.getMessage("code.1004",null, currentLocale);
            error  = true;
        }
        Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,error,message,serviceInstitution,request.getRequestURI());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



    /**
     * Update Service institutions
     * @param service Institution
     * @param auth
     * @param request
     * @return ResponseEntity
     */
    @PostMapping("/update/servicetype")
    public ResponseEntity<?> updateServiceType(@RequestBody ServiceInstitutionDto serviceInstitutionDto,JwtAuthenticationToken auth,
                                               HttpServletRequest request) {
        ServiceInstitutionDto service = null;
        try {

            if(serviceInstitutionDto != null){

                Optional<ServiceInstitution> existingServiceInstitution = serviceInstitutionService.findById(serviceInstitutionDto.getId());
                AuthDetailsDto authDetails = utils.getTokenAuthenticationDetails(auth);

                if(existingServiceInstitution.isPresent()){

                    ServiceInstitution otherServiceInstitutions = serviceInstitutionService.findByInstitutionCodeAndRecordStatusId(serviceInstitutionDto.getInstitutionCode(), Constants.RECORD_STATUS_ACTIVE);
                    if(otherServiceInstitutions == null || otherServiceInstitutions.getId().equals(existingServiceInstitution.get().getId())) {

                        Optional<ServiceInstitutionCategory> serviceInstitutionCategory = serviceInstitutionCategoryService.findById(serviceInstitutionDto.getServiceInstitutionCategoryId());
                        if(serviceInstitutionCategory.isPresent()){

                            existingServiceInstitution.get().setInstitutionCode(serviceInstitutionDto.getInstitutionCode());
                            existingServiceInstitution.get().setInstitutionNumber(serviceInstitutionDto.getInstitutionNumber());
                            existingServiceInstitution.get().setRecordStatusId(serviceInstitutionDto.getRecordStatusId());
                            existingServiceInstitution.get().setUpdatedBy(authDetails.getUserId());
                            existingServiceInstitution.get().setUpdatedByUserName(authDetails.getUserName());
                           existingServiceInstitution.get().setEmail(serviceInstitutionDto.getEmail());
                           existingServiceInstitution.get().setPhysicalAddress(serviceInstitutionDto.getPhysicalAddress());
                           existingServiceInstitution.get().setPostalAddress(serviceInstitutionDto.getPostalAddress());
                           existingServiceInstitution.get().setPrimaryPhoneNumber(serviceInstitutionDto.getPrimaryPhoneNumber());
                           existingServiceInstitution.get().setSecondaryPhoneNumber(serviceInstitutionDto.getSecondaryPhoneNumber());
                         //  existingServiceInstitution.get().setServiceInstitutionCategory(serviceInstitutionCategory.get().getId());

                            ServiceInstitution updatedServiceInstitutions = serviceInstitutionService.saveServiceInstitution(existingServiceInstitution.get());
                            if(updatedServiceInstitutions != null) {

                                service = new ServiceInstitutionDto(updatedServiceInstitutions.getId(),String.valueOf(updatedServiceInstitutions.getServiceInstitutionUid()),
                                        updatedServiceInstitutions.getInstitutionName(),updatedServiceInstitutions.getPostalAddress(),updatedServiceInstitutions.getInstitutionCode(),
                                        updatedServiceInstitutions.getInstitutionNumber(),updatedServiceInstitutions.getPostalAddress(),updatedServiceInstitutions.getPhysicalAddress(),
                                        updatedServiceInstitutions.getEmail(),updatedServiceInstitutions.getServiceInstitutionCategory().getId(),
                                        updatedServiceInstitutions.getRecordStatusId());

                                message = messageSource.getMessage("general.update.success", new Object[] { "Service Type" },currentLocale);
                                status = messageSource.getMessage("code.1001", null, currentLocale);
                                error = false;

                            } else {
                                message = messageSource.getMessage("general.update.failure", new Object[] { "Service Type" },currentLocale);
                                status = messageSource.getMessage("code.1001", null, currentLocale);
                                error = true;
                            }

                        }else {

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

