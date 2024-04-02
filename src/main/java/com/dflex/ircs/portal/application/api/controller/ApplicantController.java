package com.dflex.ircs.portal.application.api.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.dflex.ircs.portal.application.dto.ApplicantDto;
import com.dflex.ircs.portal.application.entity.Applicant;
import com.dflex.ircs.portal.application.service.ApplicantService;
import com.dflex.ircs.portal.config.AuthDetailsDto;
import com.dflex.ircs.portal.util.Constants;
import com.dflex.ircs.portal.util.Response;
import com.dflex.ircs.portal.util.Utils;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 
 * @author Augustino Mwageni
 * 
 * Applicant Api Controller
 *
 */
@RestController
@RequestMapping("/api/applicant")
public class ApplicantController {

    @Autowired
    private ApplicantService applicantService;
    
    @Autowired
    private Utils utils;
    
	@Autowired
	private MessageSource messageSource;

    Locale currentLocale = LocaleContextHolder.getLocale();
	
	String status = "";
	String message = "";
	Boolean error = false;

    protected Logger logger = LoggerFactory.getLogger(ApplicantController.class);
    
    /**
     * Get Applied Costs
     * @param applicationUid
     * @param request
     * @return ResponseEntity
     */
    
    @PostMapping("/list")
    public ResponseEntity<?> getApplicantList(HttpServletRequest request) {

    	List<ApplicantDto> applicants = new ArrayList<>();
        try {
        	
    		List<Applicant> applicantList = applicantService.findByRecordStatusId(Constants.RECORD_STATUS_ACTIVE);
    		if(applicantList != null && !applicantList.isEmpty()) {
        			
    			for(Applicant apl : applicantList) {
    				
    				applicants.add(new ApplicantDto(apl.getId(),
    						String.valueOf(apl.getApplicantUid()),apl.getApplicantAccount(),
    						apl.getApplicantName(),apl.getIdentityNumber(),apl.getIdentityTypeId(),apl.getIdentityTypeName(),
    						apl.getNationality(),apl.getTelephoneNumber(),apl.getMobileNumber(),apl.getWhatsappNumber(),apl.getEmailAddress(),
    						apl.getPostalAddress(),apl.getPlotNumber(),apl.getStreet(),apl.getWard(),apl.getLocation(),
    						apl.getGenderId(),apl.getGenderName(),apl.getLocationLatitude(),apl.getLocationLongitude(),
    						apl.getBlockCode(),apl.getBlockNumber(),apl.getRecordStatusId()));
    			}
        		message = messageSource.getMessage("message.1001", null, currentLocale);
				status = messageSource.getMessage("code.1001", null, currentLocale);
				error = false;
			} else {
				message = messageSource.getMessage("message.1007", null, currentLocale);
				status = messageSource.getMessage("code.1007", null, currentLocale);
				error = true;
			}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		message = messageSource.getMessage("message.1004",null, currentLocale);
			status = messageSource.getMessage("code.1004",null, currentLocale);
			error  = true;
    	}
        Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,error,message,applicants,request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
    }

   
    /**
     * Create Applicant
     * @param applicantDto
     * @param auth
     * @param request
     * @return ResponseEntity
     */
	@PostMapping("/create")
    public ResponseEntity<?> createApplicant(@RequestBody ApplicantDto applicantDto,
    		JwtAuthenticationToken auth, HttpServletRequest request) {
		
		Applicant applicant = null;

		try {
			
			if(applicantDto != null) {
				
				AuthDetailsDto authDetails = utils.getTokenAuthenticationDetails(auth);
				
				String applicantAccount = utils.generateRandomNumber(8);
				Applicant newApp = new Applicant(authDetails.getUserId(),authDetails.getUserName(), applicantAccount,applicantDto.getApplicantName(),
						applicantDto.getIdentityNumber(),applicantDto.getIdentityTypeId(),applicantDto.getIdentityTypeName(),
						applicantDto.getNationality(),applicantDto.getTelephoneNumber(), applicantDto.getMobileNumber(),
						applicantDto.getWhatsappNumber(), applicantDto.getEmailAddress(),applicantDto.getPostalAddress(),
						applicantDto.getPlotNumber(), applicantDto.getStreet(), applicantDto.getWard(), applicantDto.getLocation(),
						applicantDto.getGenderId(),applicantDto.getGenderName(),applicantDto.getLocationLatitude(),
						applicantDto.getLocationLongitude(),applicantDto.getBlockCode(),applicantDto.getBlockNumber());
				
				applicant = applicantService.saveApplicant(newApp);
				if (applicant != null) {
					message = messageSource.getMessage("general.create.success", new Object[] { "Applicant" },currentLocale);
					status = messageSource.getMessage("code.1001", null, currentLocale);
					error = false;
				} else {
					message = messageSource.getMessage("general.create.failure", new Object[] { "Applicant" },currentLocale);
					status = messageSource.getMessage("code.1002", null, currentLocale);
					error = true;
				}
			} else {
				message = messageSource.getMessage("message.1005",null, currentLocale);
				status = messageSource.getMessage("code.1005", null, currentLocale);
				error = true;
			}
		} catch (Exception ex) {
			
			message = messageSource.getMessage("general.create.failure", new Object[] { "Applicant" }, currentLocale);
			status = messageSource.getMessage("code.1004", null, currentLocale);
			logger.error(message, ex);
			error = true;
		}

		Response response = new Response(String.valueOf(Calendar.getInstance().getTime()), status, error, message,applicant, request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
    }
	
	
	/**
     * Update Applicant
     * @param applicantDto
     * @param auth
     * @param request
     * @return ResponseEntity
     */
	@PostMapping("/update")
    public ResponseEntity<?> updateApplicant(@RequestBody ApplicantDto applicantDto,
    		JwtAuthenticationToken auth, HttpServletRequest request) {
		
		Applicant applicant = null;

		try {
			
			AuthDetailsDto authDetails = utils.getTokenAuthenticationDetails(auth);
			if(applicantDto != null && applicantDto.getId() != null) {
				
				Optional<Applicant> app = applicantService.findById(applicantDto.getId());
				if(app.isPresent()) {
					
					app.get().setApplicantName(applicantDto.getApplicantName());
					app.get().setBlockCode(applicantDto.getBlockCode());
					app.get().setBlockNumber(applicantDto.getBlockNumber());
					app.get().setEmailAddress(applicantDto.getEmailAddress());
					app.get().setGenderId(applicantDto.getGenderId());
					app.get().setGenderName(applicantDto.getGenderName());
					app.get().setIdentityNumber(applicantDto.getIdentityNumber());
					app.get().setIdentityTypeId(applicantDto.getIdentityTypeId());
					app.get().setIdentityTypeName(applicantDto.getIdentityTypeName());
					app.get().setLocation(applicantDto.getLocation());
					app.get().setLocationLatitude(applicantDto.getLocationLatitude());
					app.get().setLocationLongitude(applicantDto.getLocationLongitude());
					app.get().setMobileNumber(applicantDto.getMobileNumber());
					app.get().setNationality(applicantDto.getNationality());
					app.get().setPlotNumber(applicantDto.getPlotNumber());
					app.get().setPostalAddress(applicantDto.getPostalAddress());
					app.get().setStreet(applicantDto.getStreet());
					app.get().setTelephoneNumber(applicantDto.getTelephoneNumber());
					app.get().setUpdatedBy(authDetails.getUserId());
					app.get().setUpdatedByUserName(authDetails.getUserName());
					app.get().setUpdatedDate(new Date());
					
					applicant = applicantService.saveApplicant(app.get());
					
					if (applicant != null) {
						message = messageSource.getMessage("general.update.success", new Object[] { "Applicant" },currentLocale);
						status = messageSource.getMessage("code.1001", null, currentLocale);
						error = false;
					} else {
						message = messageSource.getMessage("general.update.failure", new Object[] { "Applicant" },currentLocale);
						status = messageSource.getMessage("code.1002", null, currentLocale);
						error = true;
					}
				} else {
					message = messageSource.getMessage("message.1007",null, currentLocale);
					status = messageSource.getMessage("code.1007", null, currentLocale);
					error = true;
				}
			} else {
				message = messageSource.getMessage("message.1005",null, currentLocale);
				status = messageSource.getMessage("code.1005", null, currentLocale);
				error = true;
			}
		} catch (Exception ex) {
			
			message = messageSource.getMessage("general.create.failure", new Object[] { "Applicant" }, currentLocale);
			status = messageSource.getMessage("code.1004", null, currentLocale);
			logger.error(message, ex);
			error = true;
		}

		Response response = new Response(String.valueOf(Calendar.getInstance().getTime()), status, error, message,applicant, request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
