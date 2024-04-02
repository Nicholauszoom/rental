package com.dflex.ircs.portal.license.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dflex.ircs.portal.application.dto.ApplicantDto;
import com.dflex.ircs.portal.application.entity.Applicant;
import com.dflex.ircs.portal.application.service.ApplicantService;
import com.dflex.ircs.portal.config.AuthDetailsDto;
import com.dflex.ircs.portal.license.dto.LicenseDto;
import com.dflex.ircs.portal.license.service.LicenseService;
import com.dflex.ircs.portal.util.Constants;
import com.dflex.ircs.portal.util.Response;
import com.dflex.ircs.portal.util.Utils;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 
 * @author Augustino Mwageni
 * 
 * License Api Controller
 *
 */
@RestController
@RequestMapping("/api/license")
public class LicenseController {

    @Autowired
    private ApplicantService applicantService;
    
    @Autowired
    private LicenseService licenseService;
    
    @Autowired
    private Utils utils;
    
	@Autowired
	private MessageSource messageSource;

    Locale currentLocale = LocaleContextHolder.getLocale();
	
	String status = "";
	String message = "";
	Boolean error = false;

    protected Logger logger = LoggerFactory.getLogger(LicenseController.class);
    
    /**
     * Get License List by Search Criteria
     * @param request
     * @return ResponseEntity
     */
    @PostMapping("/search/limit/{limit}")
    public ResponseEntity<?> searchLicenseBySearchCriteria(@RequestBody LicenseDto searchCriteria,
    		@PathVariable("limit") Long recordLimit,HttpServletRequest request) {
    	
    	System.out.println("searchCriteria***************"+searchCriteria);

    	List<LicenseDto> licenseList = new ArrayList<>();
    	
        try {
        	String licenseNumber = searchCriteria.getLicenseNumber()==null?"":searchCriteria.getLicenseNumber().toUpperCase();
        	String licenseName = searchCriteria.getLicenseName()==null?"":searchCriteria.getLicenseName().toUpperCase();
        	String applicantAccount = searchCriteria.getOwnerAccount()==null?"":searchCriteria.getOwnerAccount().toUpperCase();
        	String applicantName = searchCriteria.getOwnerName()==null?"":searchCriteria.getOwnerName().toUpperCase();
        	String identityNumber = searchCriteria.getOwnerIdentityNumber()==null?"":searchCriteria.getOwnerIdentityNumber().toUpperCase();
        	
        	licenseList = licenseService. findBySeachCriteriaAndRecordStatusId(licenseNumber,licenseName,
        			Constants.RECORD_STATUS_ACTIVE,applicantAccount,applicantName,identityNumber,recordLimit);
    		if(licenseList != null && !licenseList.isEmpty()) {
        			
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
        Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,error,message,licenseList,request.getRequestURI());
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
		System.out.println("applicantDto***********"+applicantDto);
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
					
					System.out.println("app.get()***********"+app.get());
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
