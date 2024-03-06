package com.dflex.ircs.portal.module.controller;

import java.util.ArrayList;
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
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dflex.ircs.portal.module.dto.AppFormFieldDto;
import com.dflex.ircs.portal.module.entity.AppFormField;
import com.dflex.ircs.portal.module.service.AppFormFieldService;
import com.dflex.ircs.portal.util.Constants;
import com.dflex.ircs.portal.util.Response;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 
 * @author Augustino Mwageni
 * 
 * Api Controller class for AppFormField
 *
 */
@RestController
@RequestMapping("/api/appformfield")
public class AppFormFieldController {

	@Autowired
    private AppFormFieldService appFormFieldService;
	
	@Autowired
	private MessageSource messageSource;
	
	Locale currentLocale = LocaleContextHolder.getLocale();
	
	String status = "";
	String message = "";
	Boolean error = false;

    protected Logger logger = LoggerFactory.getLogger(AppFormFieldController.class);

    @PostMapping("/list/appformuid/{appformuid}")
    public ResponseEntity<?> getApplicationFormFieldByFormUid (@PathVariable("appformuid") String appFormUid,
    		JwtAuthenticationToken auth, HttpServletRequest request) {
    	
    	List<AppFormFieldDto> formFields = null;
    	try {
    		
    		List<AppFormField> appFormFields = appFormFieldService.findByAppFormUidAndRecordStatusId(
    				UUID.fromString(appFormUid),Constants.RECORD_STATUS_ACTIVE);
    		if(appFormFields != null && !appFormFields.isEmpty()) {
    			
    			formFields = new ArrayList<>();
    			for(AppFormField field : appFormFields) {
    				
    				formFields.add(new AppFormFieldDto(field.getId(),String.valueOf(field.getAppFormFieldUid()),field.getFieldType(),
    						field.getValueMinimumSize(),field.getValueMaximumSize(),field.getValueDefault(),field.getDataType(),
    						field.getFormDisplayText(),field.getListDisplayText(),field.getDataFieldName(),field.getDataFieldKey(),field.getDataSource(),
    						field.getRecordStatusId(),field.getAppLookupType()==null?"":String.valueOf(field.getAppLookupType().getAppLookupTypeUid()),
    						field.getAppForm().getId(),field.getDisplaySizeClass(),field.getValidation(),field.getShowOnList(),field.getIsIdentity(),
    						field.getIsPartOfName()));
    			}
    			message = messageSource.getMessage("message.1001",null, currentLocale);
    			status = messageSource.getMessage("code.1001",null, currentLocale);
    			error  = false;
    			
    		} else {
    			message = messageSource.getMessage("message.1007",null, currentLocale);
    			status = messageSource.getMessage("code.1007",null, currentLocale);
    			error  = true;
    		}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		message = messageSource.getMessage("message.1004",null, currentLocale);
			status = messageSource.getMessage("code.1004",null, currentLocale);
			error  = true;
    	}
    	Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,error,message,formFields,request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}


