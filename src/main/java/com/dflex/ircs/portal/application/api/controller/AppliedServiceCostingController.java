package com.dflex.ircs.portal.application.api.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
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

import com.dflex.ircs.portal.application.dto.AppliedServiceCostingDto;
import com.dflex.ircs.portal.application.entity.AppliedServiceCosting;
import com.dflex.ircs.portal.application.service.AppliedServiceCostingService;
import com.dflex.ircs.portal.data.entity.FormData1;
import com.dflex.ircs.portal.data.service.FormDataService;
import com.dflex.ircs.portal.util.Constants;
import com.dflex.ircs.portal.util.Response;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/applied-costing")
public class AppliedServiceCostingController {

    @Autowired
    private AppliedServiceCostingService appliedServiceCostingService;
    
    @Autowired
    private FormDataService formDataService;
    
	@Autowired
	private MessageSource messageSource;

    Locale currentLocale = LocaleContextHolder.getLocale();
	
	String status = "";
	String message = "";
	Boolean error = false;

    protected Logger logger = LoggerFactory.getLogger(AppliedServiceCostingController.class);
    
    /**
     * Get Applied Costs
     * @param applicationUid
     * @param request
     * @return ResponseEntity
     */
    @PostMapping("/list/applicationuid/{applicationuid}")
    public ResponseEntity<?> applicationAppliedCostingList(@PathVariable("applicationuid")String applicationUid,HttpServletRequest request) {

    	List<AppliedServiceCostingDto> appliedCosts = new ArrayList<>();
        try {
        	
    		Optional<FormData1> application = formDataService.findFormData1ByApplicationUid(
    				UUID.fromString(applicationUid));
    		
    		List<AppliedServiceCosting> serviceCosting = appliedServiceCostingService
    				.findByReferenceApplicationIdAndReferenceApplicationTableAndRecordStatusId(application.get().getId(),
    						Constants.FORM_DATA_1,Constants.RECORD_STATUS_ACTIVE);
    		if(serviceCosting != null && !serviceCosting.isEmpty()) {
        			
    			for(AppliedServiceCosting costing : serviceCosting) {
    				
    				appliedCosts.add(new AppliedServiceCostingDto(costing.getId(),
    						String.valueOf(costing.getAppliedServiceCostingUid()),costing.getReferenceApplicationId(),
    						costing.getReferenceApplicationTable(),costing.getServiceTypeCode(),costing.getServiceTypeName(),
    						costing.getRevenueSource().getId(),costing.getUnitCost(),costing.getUnitCount(),costing.getTotalCost(),
    						costing.getCurrencyId(),costing.getCurrencyCode(),costing.getExchangeRate(),costing.getAmountPaid(),
    						costing.getPaymentStatus()));
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
        Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,error,message,appliedCosts,request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
