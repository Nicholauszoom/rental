package com.dflex.ircs.portal.setup.controller;

import java.util.*;

import com.dflex.ircs.portal.setup.entity.*;
import com.dflex.ircs.portal.setup.entity.Currency;
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
import com.dflex.ircs.portal.setup.dto.RevenueSourceEstimateDto;
import com.dflex.ircs.portal.setup.service.CurrencyService;
import com.dflex.ircs.portal.setup.service.FinancialYearService;
import com.dflex.ircs.portal.setup.service.RevenueSourceEstimateService;
import com.dflex.ircs.portal.setup.service.RevenueSourceService;
import com.dflex.ircs.portal.util.Constants;
import com.dflex.ircs.portal.util.Response;
import com.dflex.ircs.portal.util.Utils;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 
 * @author Augustino Mwageni
 * 
 * Api Controller class for RevenueSoureEstimate
 *
 */
@RestController
@RequestMapping("/api/revenuesource-estimate")
public class RevenueSourceEstimateController {
	
	@Autowired 
	private RevenueSourceEstimateService revenueSourceEstimateService;
	
	@Autowired 
	private RevenueSourceService revenueSourceService;
	
	@Autowired 
	private CurrencyService currencyService;
	
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
	
	protected Logger logger = LoggerFactory.getLogger(RevenueSourceController.class);

	/**
	 * List Reveunue Source Estimate
	 * @param request
	 * @return ResponseEntity
	 */

	@PostMapping("/list")
	public ResponseEntity<?> getListRevenueSources(HttpServletRequest request) {

		List<RevenueSourceEstimateDto> revenueSourceEstimateDtoList = new ArrayList<>();
		try {
			List<RevenueSourceEstimate>  revenueSourceEstimates = revenueSourceEstimateService.findAll();
			if(revenueSourceEstimates != null){
				for (RevenueSourceEstimate revenueSource : revenueSourceEstimates) {
					RevenueSourceEstimateDto sourceEstimateDto = new RevenueSourceEstimateDto();
					sourceEstimateDto.setId(revenueSource.getId());
					sourceEstimateDto.setRevenueSourceId(revenueSource.getRevenueSource().getId());
					sourceEstimateDto.setCurrenyId(revenueSource.getCurrency().getId());
					sourceEstimateDto.setRevenueSourceEstimateUid(revenueSource.getRevenueSourceEstimateUid().toString());
					sourceEstimateDto.setRevenueTarget(revenueSource.getRevenueTarget());
					sourceEstimateDto.setRecordStatusId(revenueSource.getRecordStatusId());

					revenueSourceEstimateDtoList.add(sourceEstimateDto);

				}
				message = messageSource.getMessage("message.1001", null, currentLocale);
				status = messageSource.getMessage("code.1001", null, currentLocale);
				error = false;
			} else {
				message = messageSource.getMessage("message.1007", null, currentLocale);
				status = messageSource.getMessage("code.1007", null, currentLocale);
				error = true;
			}
			Response response = new Response(String.valueOf(Calendar.getInstance().getTime()), status, error, message, revenueSourceEstimateDtoList, request.getRequestURI());
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
	 * Create New Reveunue Source Estimate
	 * @param revenueSourceEstimateDto
	 * @return ResponseEntity
	 */
	@PostMapping("/create/revenuesource-estimate")
    public ResponseEntity<?> createRevenueSourceEstimate(@RequestBody RevenueSourceEstimateDto revenueSourceEstimateDto,
    		JwtAuthenticationToken auth, HttpServletRequest request) {
		
		RevenueSourceEstimate revenueSourceEstimate = null;

		try {
			
			if(revenueSourceEstimateDto != null) {
				
				AuthDetailsDto authDetails = utils.getTokenAuthenticationDetails(auth);
				
				RevenueSourceEstimate oldRevenueSourceEstimate = revenueSourceEstimateService.findByRevenueSourceIdAndFinancialYearIdAndRecordStatusId(
						revenueSourceEstimateDto.getRevenueSourceId(),revenueSourceEstimateDto.getFinancialYearId(),Constants.RECORD_STATUS_ACTIVE);
				if (oldRevenueSourceEstimate == null) {
					
					Optional<Currency> currency = currencyService.findById(revenueSourceEstimateDto.getCurrenyId());
					Optional<FinancialYear> finacialYear = financialYearService.findById(revenueSourceEstimateDto.getFinancialYearId());
					Optional<RevenueSource> revenueSource  = revenueSourceService.findById(revenueSourceEstimateDto.getRevenueSourceId());
					
					if(currency.isPresent() && finacialYear.isPresent() && revenueSource.isPresent()) {
						
						RevenueSourceEstimate newRevenueSourceEstimate = new RevenueSourceEstimate(authDetails.getUserId(),authDetails.getUserName(),
								finacialYear.get().getId(),revenueSourceEstimateDto.getRevenueTarget(), Constants.RECORD_STATUS_ACTIVE, currency.get(),
								revenueSource.get());
						
						revenueSourceEstimate = revenueSourceEstimateService.saveRevenueSourceEstimate(newRevenueSourceEstimate);
						if (revenueSourceEstimate != null) {
							message = messageSource.getMessage("general.create.success", new Object[] { "Revenue Source Estimate" },currentLocale);
							status = messageSource.getMessage("code.1001", null, currentLocale);
							error = false;
						} else {
							message = messageSource.getMessage("general.create.failure", new Object[] { "Revenue Source Estimate" },currentLocale);
							status = messageSource.getMessage("code.1002", null, currentLocale);
							error = true;
						}
					} else {
						message = messageSource.getMessage("general.create.failure", new Object[] { "Revenue Source Estimate" },currentLocale);
						status = messageSource.getMessage("code.1002", null, currentLocale);
						error = true;
					}
				} else {
					revenueSourceEstimate = oldRevenueSourceEstimate;
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

		Response response = new Response(String.valueOf(Calendar.getInstance().getTime()), status, error, message,revenueSourceEstimate, request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
    }
	
}


