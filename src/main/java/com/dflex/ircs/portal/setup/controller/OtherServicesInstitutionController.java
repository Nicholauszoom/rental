package com.dflex.ircs.portal.setup.controller;


import com.dflex.ircs.portal.config.AuthDetailsDto;
import com.dflex.ircs.portal.setup.dto.OtherServiceInstitutionDto;
import com.dflex.ircs.portal.setup.entity.OtherServiceInstitution;
import com.dflex.ircs.portal.setup.service.OtherServiceInstitutionService;
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
@RequestMapping("/api/otherServices")
public class OtherServicesInstitutionController {


    @Autowired
    private OtherServiceInstitutionService otherServiceInstitutionService;

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
     *
     * @param request
     * @return ResponseEntity
     */
    @PostMapping("/list")
    public ResponseEntity<?> getPaymentFacilitator(HttpServletRequest request) {
        List<OtherServiceInstitutionDto> otherServiceInstitutionDtoList = new ArrayList<>();
        try {
            List<OtherServiceInstitution> service = otherServiceInstitutionService.findAll();
            if(service != null){
                for (OtherServiceInstitution othersService : service) {

                    OtherServiceInstitutionDto otherServiceDto = new OtherServiceInstitutionDto();
                    otherServiceDto.setId(othersService.getId());
                    otherServiceDto.setOtherServiceInstitutionUid(othersService.getOtherServiceInstitutionUid().toString());
                    otherServiceDto.setInstitutionCode(othersService.getInstitutionCode());
                    otherServiceDto.setEmail(othersService.getEmail());
                    otherServiceDto.setInstitutionName(othersService.getInstitutionName());
                    otherServiceDto.setPhysicalAddress(othersService.getPhysicalAddress());
                    otherServiceDto.setPostalAddress(othersService.getPostalAddress());
                    otherServiceDto.setPrimaryPhoneNumber(othersService.getPrimaryPhoneNumber());
                    otherServiceDto.setSecondaryPhoneNumber(othersService.getSecondaryPhoneNumber());
                    otherServiceDto.setRecordStatusId(othersService.getRecordStatusId());

                    otherServiceInstitutionDtoList.add(otherServiceDto);

                }
                message = messageSource.getMessage("message.1001", null, currentLocale);
                status = messageSource.getMessage("code.1001", null, currentLocale);
                error = false;
            } else {
                message = messageSource.getMessage("message.1007", null, currentLocale);
                status = messageSource.getMessage("code.1007", null, currentLocale);
                error = true;
            }
            Response response = new Response(String.valueOf(Calendar.getInstance().getTime()), status, error, message, otherServiceInstitutionDtoList, request.getRequestURI());
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
     * Create Other Service Institutions
     * @param otherServiceInstitutionDto
     * @param auth
     * @param request
     * @return ResponseEntity
     */
    @PostMapping("/create")
    public ResponseEntity<?> createServiceInstitution(@RequestBody OtherServiceInstitutionDto otherServiceInstitutionDto, JwtAuthenticationToken auth,
                                                      HttpServletRequest request) {

        OtherServiceInstitutionDto otherService = null;

        try {
            if(otherServiceInstitutionDto != null){

                AuthDetailsDto authDetails = utils.getTokenAuthenticationDetails(auth);
                logger.info("the aauth details is {}",authDetails);
                OtherServiceInstitution getPreviousService = otherServiceInstitutionService.findByInstitutionCodeAndRecordStatusId(otherServiceInstitutionDto.getInstitutionCode(),
                        Constants.RECORD_STATUS_ACTIVE);
                if(getPreviousService == null) {

                    OtherServiceInstitution institution = new OtherServiceInstitution(authDetails.getUserId(), authDetails.getUserName(),otherServiceInstitutionDto.getInstitutionCode(),
                             otherServiceInstitutionDto.getPostalAddress(), otherServiceInstitutionDto.getPhysicalAddress(), otherServiceInstitutionDto.getInstitutionName(),
                            otherServiceInstitutionDto.getSecondaryPhoneNumber(), otherServiceInstitutionDto.getPrimaryPhoneNumber(), otherServiceInstitutionDto.getEmail(),
                            otherServiceInstitutionDto.getRecordStatusId()
                    );

                    OtherServiceInstitution newOtherServiceInstitution = otherServiceInstitutionService.saveOtherServiceInstitution(institution);
                    if (newOtherServiceInstitution != null) {
                        otherService = new OtherServiceInstitutionDto(newOtherServiceInstitution.getId(), newOtherServiceInstitution.getInstitutionCode(),
                                newOtherServiceInstitution.getPhysicalAddress(), newOtherServiceInstitution.getPostalAddress(), newOtherServiceInstitution.getEmail(),newOtherServiceInstitution.getOtherServiceInstitutionUid(),
                                newOtherServiceInstitution.getInstitutionName(), newOtherServiceInstitution.getPrimaryPhoneNumber(), newOtherServiceInstitution.getSecondaryPhoneNumber(),
                                newOtherServiceInstitution.getRecordStatusId()
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


        }catch (Exception ex){
            message = messageSource.getMessage("message.1004",null, currentLocale);
            status = messageSource.getMessage("code.1004",null, currentLocale);
            error  = true;
        }
        Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,error,message,otherService,request.getRequestURI());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    /**
     * Update other Service institutions
     * @param otherServiceInstitutionDto
     * @param auth
     * @param request
     * @return ResponseEntity
     */
    @PostMapping("/update")
    public ResponseEntity<?> updateServiceType(@RequestBody OtherServiceInstitutionDto otherServiceInstitutionDto,JwtAuthenticationToken auth,
                                               HttpServletRequest request) {
        OtherServiceInstitutionDto service = null;
        try {

            if(otherServiceInstitutionDto != null){

                Optional<OtherServiceInstitution> existingOtherServiceInstitution = otherServiceInstitutionService.findById(otherServiceInstitutionDto.getId());
                AuthDetailsDto authDetails = utils.getTokenAuthenticationDetails(auth);

                if(existingOtherServiceInstitution.isPresent()){

                    OtherServiceInstitution otherServiceInstitution = otherServiceInstitutionService.findByInstitutionCodeAndRecordStatusId(otherServiceInstitutionDto.getInstitutionCode(), Constants.RECORD_STATUS_ACTIVE);
                    if(otherServiceInstitution == null || otherServiceInstitution.getId().equals(existingOtherServiceInstitution.get().getId())) {

                        OtherServiceInstitution updatedServiceInstitution = existingOtherServiceInstitution.get();

                        updatedServiceInstitution.setInstitutionCode(otherServiceInstitutionDto.getInstitutionCode());
                        updatedServiceInstitution.setRecordStatusId(otherServiceInstitutionDto.getRecordStatusId());
                        updatedServiceInstitution.setUpdatedBy(authDetails.getUserId());
                        updatedServiceInstitution.setUpdatedByUserName(authDetails.getUserName());
                        updatedServiceInstitution.setEmail(otherServiceInstitutionDto.getEmail());
                        updatedServiceInstitution.setPhysicalAddress(otherServiceInstitutionDto.getPhysicalAddress());
                        updatedServiceInstitution.setPostalAddress(otherServiceInstitutionDto.getPostalAddress());
                        updatedServiceInstitution.setPrimaryPhoneNumber(otherServiceInstitutionDto.getPrimaryPhoneNumber());
                        updatedServiceInstitution.setSecondaryPhoneNumber(otherServiceInstitutionDto.getSecondaryPhoneNumber());

                        OtherServiceInstitution savedServiceInstitution = otherServiceInstitutionService.saveOtherServiceInstitution(updatedServiceInstitution);

                        if(savedServiceInstitution != null) {

                            service = new OtherServiceInstitutionDto(savedServiceInstitution.getId(), savedServiceInstitution.getInstitutionCode(),
                                    savedServiceInstitution.getPhysicalAddress(), savedServiceInstitution.getPostalAddress(), savedServiceInstitution.getEmail(),savedServiceInstitution.getOtherServiceInstitutionUid(),
                                    savedServiceInstitution.getInstitutionName(), savedServiceInstitution.getPrimaryPhoneNumber(), savedServiceInstitution.getSecondaryPhoneNumber(),
                                    savedServiceInstitution.getRecordStatusId()
                                    );
                            message = messageSource.getMessage("general.update.success", new Object[] { "Other Service Institution" },currentLocale);
                            status = messageSource.getMessage("code.1001", null, currentLocale);
                            error = false;

                        } else {
                            message = messageSource.getMessage("general.update.failure", new Object[] { "Other Service Institution" },currentLocale);
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


