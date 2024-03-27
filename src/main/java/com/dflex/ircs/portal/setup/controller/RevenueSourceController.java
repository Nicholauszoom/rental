package com.dflex.ircs.portal.setup.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

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

import com.dflex.ircs.portal.setup.dto.RevenueSourceDetailsDto;
import com.dflex.ircs.portal.setup.service.RevenueSourceService;
import com.dflex.ircs.portal.util.Constants;
import com.dflex.ircs.portal.util.Response;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/revenuesource")
public class RevenueSourceController {

	@Autowired
    private RevenueSourceService revenueSourceService;
	
	@Autowired
	private MessageSource messageSource;
	
	Locale currentLocale = LocaleContextHolder.getLocale();


    protected Logger logger = LoggerFactory.getLogger(RevenueSourceController.class);



	@PostMapping("/department/{departmentuid}/module/{moduleuid}")
	public ResponseEntity<Response> getInstitutionDepartmentRevenueSourcesByDepartmentUidAndModuleUid(
			@PathVariable("departmentuid") String departmentUid,
			@PathVariable("moduleuid") String moduleUid,
			HttpServletRequest request) {

		try {
			List<RevenueSourceDetailsDto> revenueSources = revenueSourceService.findDetailsByServiceDepartmentUidAndAppModuleUidAndRecordStatusId(
					UUID.fromString(departmentUid), UUID.fromString(moduleUid), Constants.RECORD_STATUS_ACTIVE);
			return generateResponse(revenueSources, "message.1001", "code.1001", request);
		} catch (Exception ex) {
			ex.printStackTrace();
			return generateResponse(null, "message.1004", "code.1004", request);
		}
	}


	@PostMapping("/department/{departmentuid}")
	public ResponseEntity<Response> getInstitutionDepartmentRevenueSources(
			@PathVariable("departmentuid") String departmentUid,
			HttpServletRequest request) {

		try {
			List<RevenueSourceDetailsDto> revenueSources = revenueSourceService.findDetailsByServiceDepartmentUidAndRecordStatusId(
					UUID.fromString(departmentUid), Constants.RECORD_STATUS_ACTIVE);
			return generateResponse(revenueSources, "message.1001", "code.1001", request);
		} catch (Exception ex) {
			ex.printStackTrace();
			return generateResponse(null, "message.1004", "code.1004", request);
		}
	}



	private ResponseEntity<Response> generateResponse(List<RevenueSourceDetailsDto> revenueSources, String messageCode,
													  String errorCode, HttpServletRequest request) {
		String message = messageSource.getMessage(messageCode, null, currentLocale);
		String status = messageSource.getMessage(errorCode, null, currentLocale);
		boolean error = revenueSources == null || revenueSources.isEmpty();
		Response response = new Response(String.valueOf(Calendar.getInstance().getTime()), status, error, message, revenueSources, request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}


}


