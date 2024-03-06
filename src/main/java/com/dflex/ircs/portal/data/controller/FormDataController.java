package com.dflex.ircs.portal.data.controller;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
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

import com.dflex.ircs.portal.config.AuthDetailsDto;
import com.dflex.ircs.portal.data.entity.FormData1;
import com.dflex.ircs.portal.data.service.FormDataService;
import com.dflex.ircs.portal.module.entity.AppForm;
import com.dflex.ircs.portal.module.entity.AppFormField;
import com.dflex.ircs.portal.module.service.AppFormFieldService;
import com.dflex.ircs.portal.module.service.AppFormService;
import com.dflex.ircs.portal.util.Constants;
import com.dflex.ircs.portal.util.Response;
import com.dflex.ircs.portal.util.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 
 * @author Augustino Mwageni
 * 
 * Api Controller class for All Form Data
 *
 */
@RestController
@RequestMapping("/api/formdata")
public class FormDataController {

	@Autowired
    private FormDataService formDataService;
	
	@Autowired
    private AppFormService appFormService;
	
	@Autowired
    private AppFormFieldService appFormFieldService;
	
	@Autowired
    private Utils utils;
	
	@Autowired
	private MessageSource messageSource;
	
	Locale currentLocale = LocaleContextHolder.getLocale();
	
	String status = "";
	String message = "";
	Boolean error = false;

    protected Logger logger = LoggerFactory.getLogger(FormDataController.class);

    @PostMapping("/create/data/{path}")
    public ResponseEntity<?> processFormData1SavingProcess(@RequestBody String data,
    		@PathVariable("path") String dataPath,JwtAuthenticationToken auth,HttpServletRequest request) {
    	
    	try {
    		System.out.println("data***************************"+data);
    		AuthDetailsDto authDetails = utils.getTokenAuthenticationDetails(auth);
    		
    		ObjectMapper objectMapper = new ObjectMapper();
    		if(dataPath.equals(Constants.DATA_PATH_1)) {
    			
    			FormData1 formData = objectMapper.readValue(data,FormData1.class);
    			formData.setCreatedBy(authDetails.getUserId());
        		formData.setCreatedByUserName(authDetails.getUserName());
        		
    			FormData1 newFormData = formDataService.saveFormData1(formData);
    			if(newFormData != null) {
    				
    				message = messageSource.getMessage("general.create.success", new Object[] { "Application Data" },currentLocale);
					status = messageSource.getMessage("code.1001", null, currentLocale);
					error = false;
					
    			} else {
    				message = messageSource.getMessage("general.create.failure", new Object[] { "Application Data" },currentLocale);
					status = messageSource.getMessage("code.1002", null, currentLocale);
					error = true;
    			}
    		}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		message = messageSource.getMessage("message.1004",null, currentLocale);
			status = messageSource.getMessage("code.1004",null, currentLocale);
			error  = true;
    	}
		Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,error,message,null,request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    /**
     * Get Form List Data
     * @param appFormUid
     * @param auth
     * @param request
     * @return ResponseEntity
     */
    @PostMapping("/list/appform/{appformuid}")
    public ResponseEntity<?> getFormListDataByAppFormUid(@PathVariable("appformuid") String appFormUid,JwtAuthenticationToken auth
    		,HttpServletRequest request) {
    	
    	List<LinkedHashMap<String,Object>> listData = null;
    	
    	try {
    		
    		AppForm appForm = appFormService.findByAppFormUidAndRecordStatusId(UUID.fromString(appFormUid), Constants.RECORD_STATUS_ACTIVE);
    		List<String> dataListFields = appFormFieldService.findAppFormDataListFieldsByAppFormUid(UUID.fromString(appFormUid));
    		
    		if(appForm != null && dataListFields != null && !dataListFields.isEmpty()) {
    			
    			dataListFields.addAll(Constants.DATA_LIST_DEFAULT_FIELDS);
    			
    			String dataPath = appForm.getFormDataTable().getFormDataTablePath();
    			listData  = formDataService.findAppFormDataListByAppFormUidAndDataPathAndDataFields(UUID.fromString(appFormUid),dataPath,dataListFields);
    			
    			System.out.println("listData***************"+listData);
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
		Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,error,message,listData,request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
  
    /**
     * Get Form Details Data
     * @param appFormUid
     * @param applicationUid
     * @param auth
     * @param request
     * @return ResponseEntity
     */
    @PostMapping("/details/appform/{appformuid}/application/{applicationuid}")
    public ResponseEntity<?> getFormDetailsDataByAppFormUidAndApplicationUid(@PathVariable("appformuid") String appFormUid,
    		@PathVariable("applicationuid") String applicationUid,JwtAuthenticationToken auth,HttpServletRequest request) {
    	
    	LinkedHashMap<String,Object> data = null;
    	
    	try {
    		System.out.println("dataDetailFields********************"+UUID.fromString(appFormUid));
    		AppForm appForm = appFormService.findByAppFormUidAndRecordStatusId(UUID.fromString(appFormUid), Constants.RECORD_STATUS_ACTIVE);
    		List<AppFormField> dataDetailFields = appFormFieldService.findByAppFormUidAndShowOnDetailAndRecordStatusId(UUID.fromString(appFormUid),
    				true,Constants.RECORD_STATUS_ACTIVE);
    		System.out.println("dataDetailFields********************"+dataDetailFields);
    		if(appForm != null && dataDetailFields != null && !dataDetailFields.isEmpty()) {
    			
    			String dataPath = appForm.getFormDataTable().getFormDataTablePath();
    			data  = formDataService.findAppFormDataDetailByAppFormUidAndApplicationUidAndDataPathAndDataFields(
    					UUID.fromString(appFormUid),UUID.fromString(applicationUid),dataPath,dataDetailFields);
    			
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
		Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,error,message,data,request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}


