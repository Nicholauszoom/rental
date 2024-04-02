package com.dflex.ircs.portal.data.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.dflex.ircs.portal.application.entity.Applicant;
import com.dflex.ircs.portal.application.entity.ApplicationWorkFlow;
import com.dflex.ircs.portal.application.entity.AppliedServiceCosting;
import com.dflex.ircs.portal.application.service.ApplicantService;
import com.dflex.ircs.portal.application.service.ApplicationWorkFlowService;
import com.dflex.ircs.portal.application.service.AppliedServiceCostingService;
import com.dflex.ircs.portal.config.AuthDetailsDto;
import com.dflex.ircs.portal.data.dto.AppProcessingDto;
import com.dflex.ircs.portal.data.entity.FormData1;
import com.dflex.ircs.portal.data.service.FormDataService;
import com.dflex.ircs.portal.invoice.api.controller.InvoiceRequestConsumerService;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceSubmissionApiReqBodyDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceSubmissionApiReqDetailDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceSubmissionApiReqDetailsDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceSubmissionApiReqHeaderDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceSubmissionApiReqServiceDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceSubmissionApiReqServicesDto;
import com.dflex.ircs.portal.module.entity.AppForm;
import com.dflex.ircs.portal.module.entity.AppFormField;
import com.dflex.ircs.portal.module.service.AppFormFieldService;
import com.dflex.ircs.portal.module.service.AppFormService;
import com.dflex.ircs.portal.setup.entity.Currency;
import com.dflex.ircs.portal.setup.entity.RevenueSource;
import com.dflex.ircs.portal.setup.entity.ServiceType;
import com.dflex.ircs.portal.setup.entity.WorkFlowCost;
import com.dflex.ircs.portal.setup.entity.WorkStation;
import com.dflex.ircs.portal.setup.service.RevenueSourceService;
import com.dflex.ircs.portal.setup.service.WorkFlowCostService;
import com.dflex.ircs.portal.setup.service.WorkStationService;
import com.dflex.ircs.portal.util.Constants;
import com.dflex.ircs.portal.util.Response;
import com.dflex.ircs.portal.util.Utils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    private ApplicantService  applicantService;
	
	@Autowired
    private AppliedServiceCostingService  appliedServiceCostingService;
	
	@Autowired
    private RevenueSourceService revenueSourceService;
	
	@Autowired
    private WorkFlowCostService workFlowCostService;
	
	@Autowired
    private ApplicationWorkFlowService applicationWorkFlowService;
	
	@Autowired
    private InvoiceRequestConsumerService invoiceRequestConsumerService;
	
	@Autowired
    private Utils utils;
	
	@Autowired
	private MessageSource messageSource;
	
	@Value("${rabbitmq.ircs.outgoing.exchange}")
	private String messageOutExchange;
	
	@Value("${rabbitmq.routingkey.prefix}")
	private String routingKeyPrefix;
	
	@Value("${rabbitmq.ircs.si.invoice.outgoing.routingkey}")
	private String invoiceOutRoutingKey;
	
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
    		JSONObject dataJson = new JSONObject(data);
    		AuthDetailsDto authDetails = utils.getTokenAuthenticationDetails(auth);
    		
    		ObjectMapper objectMapper = new ObjectMapper();
    		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    		FormData1 formData = objectMapper.readValue(data,FormData1.class);
    		if(dataPath.equals(Constants.DATA_PATH_1)) {
    			
    			String applicantUid = dataJson.getString("applicantUid");
    			Applicant applicant = applicantService.findByApplicantUid(UUID.fromString(applicantUid));
    			if(applicant != null) {
    				
    				/**
        			 * Create Application Details
        			 */
    				String applicationNumber = utils.getToken();
    				
    				
    				
        			formData.setCreatedBy(authDetails.getUserId());
            		formData.setCreatedByUserName(authDetails.getUserName());
            		formData.setApplicationNumber(applicationNumber);
            		formData.setApplicantId(applicant.getId());
            		formData.setApplicantUid(applicant.getApplicantUid());
            		formData.setWorkFlowId(Constants.WORK_FLOW_APPLICATION_ID);
            		formData.setWorkFlowName(Constants.WORK_FLOW_APPLICATION);
            		formData.setWorkFlowActionId(Constants.ACTION_APPLICATION_SUBMITTED_ID);
            		formData.setWorkFlowActionName(Constants.ACTION_APPLICATION_SUBMITTED);
            		formData.setWorkFlowRemark(Constants.ACTION_APPLICATION_SUBMITTED);
            		
            		FormData1 newFormData = formDataService.saveFormData1(formData);
            		if(newFormData != null) {
            			
            			/**
            			 * Create Applied Costs
            			 */
            			String revenueSourceUid = dataJson.getString("revenueSourceUid");
            			RevenueSource revenueSource = revenueSourceService.findByRevenueSourceUid(UUID.fromString(revenueSourceUid));
            			Long workFlowId = Long.parseLong(dataJson.getString("workFlowId"));
            			List<WorkFlowCost> workFlowCosts = workFlowCostService.findByRevenueSourceIdAndWorkFlowIdAndRecordStatusId(
            					revenueSource.getId(),workFlowId,Constants.RECORD_STATUS_ACTIVE);
            			
            			if(workFlowCosts != null && !workFlowCosts.isEmpty()) {
            				
            				for(WorkFlowCost workFlowCost : workFlowCosts) {
            					
            					ServiceType serviceType = workFlowCost.getRevenueSource().getServiceType();
                    			Currency currency = workFlowCost.getRevenueSource().getCurrency();
                    			Integer unitCount = 1;
                    			Double exchangeRate = 1.0D;
                    			 
                    			AppliedServiceCosting costing = new AppliedServiceCosting(authDetails.getUserId(),authDetails.getUserName(),newFormData.getId(),
                    					Constants.FORM_DATA_1,serviceType.getServiceTypeCode(),serviceType.getServiceTypeName(),
                    					revenueSource, revenueSource.getFixedAmount(),unitCount, revenueSource.getFixedAmount(),currency.getId(),
                    					currency.getCurrencyCode(), exchangeRate);
                    			appliedServiceCostingService.saveAppliedServiceCosting(costing);
            					
            					
            				}
            			}
            			
            			/**
            			 * Create Workflow
            			 */
            			ApplicationWorkFlow workFlow = new ApplicationWorkFlow(authDetails.getUserId(),authDetails.getUserName(),newFormData.getId(),
            					newFormData.getApplicationUid(),newFormData.getWorkFlowId(),newFormData.getWorkFlowName(),newFormData.getWorkFlowActionId(),
            					newFormData.getWorkFlowActionName(),newFormData.getWorkFlowRemark());
            			
            			applicationWorkFlowService.saveApplicationWorkFlow(workFlow);
        				
        				message = messageSource.getMessage("general.create.success", new Object[] { "Application Data" },currentLocale);
    					status = messageSource.getMessage("code.1001", null, currentLocale);
    					error = false;
    					
        			} else {
        				message = messageSource.getMessage("general.create.failure", new Object[] { "Application Data" },currentLocale);
    					status = messageSource.getMessage("code.1002", null, currentLocale);
    					error = true;
    					
        			}
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
    @PostMapping("/list/appform/{appformuid}/workflow/{workflowid}")
    public ResponseEntity<?> getFormListDataByAppFormUid(@PathVariable("appformuid") String appFormUid,
    		@PathVariable("workflowid") Long workFlowId,JwtAuthenticationToken auth ,HttpServletRequest request) {
    	
    	List<LinkedHashMap<String,Object>> listData = null;
    	
    	try {
    		
    		AppForm appForm = appFormService.findByAppFormUidAndRecordStatusId(UUID.fromString(appFormUid), Constants.RECORD_STATUS_ACTIVE);
    		List<String> dataListFields = appFormFieldService.findAppFormDataListFieldsByAppFormUid(UUID.fromString(appFormUid));
    		
    		if(appForm != null && dataListFields != null && !dataListFields.isEmpty()) {
    			
    			String dataPath = appForm.getFormDataTable().getFormDataTablePath();
    			listData  = formDataService.findAppFormDataListByAppFormUidAndDataPathAndDataFieldsAndWorkFlowId(UUID.fromString(appFormUid),
    					dataPath,dataListFields,workFlowId);
    			
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
    		
    		AppForm appForm = appFormService.findByAppFormUidAndRecordStatusId(UUID.fromString(appFormUid), Constants.RECORD_STATUS_ACTIVE);
    		List<AppFormField> dataDetailFields = appFormFieldService.findByAppFormUidAndShowOnDetailAndRecordStatusId(UUID.fromString(appFormUid),
    				true,Constants.RECORD_STATUS_ACTIVE);
    		
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
    
    /**
     * Assign Form Data
     * @param data
     * @param dataPath
     * @param auth
     * @param request
     * @return ResponseEntity
     */
    @PostMapping("/assign/data/{path}")
    public ResponseEntity<?> processFormDataAssignmentProcess(@RequestBody AppProcessingDto appProcessingDto,
    		@PathVariable("path") String dataPath,JwtAuthenticationToken auth,HttpServletRequest request) {
    	
    	try {
    		
    		AuthDetailsDto authDetails = utils.getTokenAuthenticationDetails(auth);
    		
    		if(dataPath.equals(Constants.DATA_PATH_1)) {
    			
    			Optional<FormData1> application = formDataService.findFormData1ByApplicationUid(
    					UUID.fromString(appProcessingDto.getApplicationUid()));
    			Date today = new Date();
    			if(application.isPresent()) {
    				/**
        			 * Assign Application
        			 */
    				application.get().setWorkFlowName(Constants.WORK_FLOW_REVIEW);
    				application.get().setWorkFlowId(Constants.WORK_FLOW_REVIEW_ID);
    				application.get().setWorkFlowActionName(Constants.ACTION_APPLICATION_ASSIGNED);
    				application.get().setWorkFlowActionId(Constants.ACTION_APPLICATION_ASSIGNED_ID);
    				application.get().setUpdatedBy(authDetails.getUserId());
    				application.get().setUpdatedByUserName(authDetails.getUserName());
    				application.get().setUpdatedDate(today);
    				application.get().setAssignedDate(today);
    				application.get().setAssignedUser(UUID.fromString(appProcessingDto.getUser()));
    				application.get().setWorkFlowRemark(appProcessingDto.getRemark());
    				
    				FormData1 newFormData = formDataService.saveFormData1(application.get());
    				if(newFormData != null) {
    					
    					/**
            			 * Create Workflow
            			 */
            			ApplicationWorkFlow workFlow = new ApplicationWorkFlow(authDetails.getUserId(),authDetails.getUserName(),newFormData.getId(),
            					newFormData.getApplicationUid(),newFormData.getWorkFlowId(),newFormData.getWorkFlowName(),newFormData.getWorkFlowActionId(),
            					newFormData.getWorkFlowActionName(),newFormData.getWorkFlowRemark());
            			
            			applicationWorkFlowService.saveApplicationWorkFlow(workFlow);
        				
        				message = messageSource.getMessage("general.create.success", new Object[] { "Application Assignment" },currentLocale);
    					status = messageSource.getMessage("code.1001", null, currentLocale);
    					error = false;
    					
    				} else {
    					message = messageSource.getMessage("general.create.failure", new Object[] { "Application Assignment" },currentLocale);
    					status = messageSource.getMessage("code.1002", null, currentLocale);
    					error = true;
    				}
            	} else {
					message = messageSource.getMessage("message.1007", null, currentLocale);
					status = messageSource.getMessage("code.1007", null, currentLocale);
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
     * Review Form Data
     * @param data
     * @param dataPath
     * @param auth
     * @param request
     * @return ResponseEntity
     */
    @PostMapping("/review/data/{path}")
    public ResponseEntity<?> processFormDataReviewProcess(@RequestBody AppProcessingDto appProcessingDto,
    		@PathVariable("path") String dataPath,JwtAuthenticationToken auth,HttpServletRequest request) {
    	
    	try {
    		
    		AuthDetailsDto authDetails = utils.getTokenAuthenticationDetails(auth);
    		
    		if(dataPath.equals(Constants.DATA_PATH_1)) {
    			
    			Optional<FormData1> application = formDataService.findFormData1ByApplicationUid(
    					UUID.fromString(appProcessingDto.getApplicationUid()));
    			Date today = new Date();
    			if(application.isPresent()) {
    				/**
        			 * Assign Application
        			 */
    				if(appProcessingDto.getDecision().equals(Constants.DECISION_ACCEPT)) {
    					application.get().setWorkFlowName(Constants.WORK_FLOW_APPROVAL);
        				application.get().setWorkFlowId(Constants.WORK_FLOW_APPROVAL_ID);
        				application.get().setWorkFlowActionName(Constants.ACTION_REVIEW_ACCEPTED);
        				application.get().setWorkFlowActionId(Constants.ACTION_REVIEW_ACCEPTED_ID);
    				} else {
    					application.get().setWorkFlowActionName(Constants.ACTION_REVIEW_REJECTED);
        				application.get().setWorkFlowActionId(Constants.ACTION_REVIEW_REJECTED_ID);
    				}
    				
    				application.get().setUpdatedBy(authDetails.getUserId());
    				application.get().setUpdatedByUserName(authDetails.getUserName());
    				application.get().setUpdatedDate(today);
    				application.get().setWorkFlowRemark(appProcessingDto.getRemark());
    				
    				FormData1 newFormData = formDataService.saveFormData1(application.get());
    				if(newFormData != null) {
    					
    					/**
            			 * Create Workflow
            			 */
            			ApplicationWorkFlow workFlow = new ApplicationWorkFlow(authDetails.getUserId(),authDetails.getUserName(),newFormData.getId(),
            					newFormData.getApplicationUid(),newFormData.getWorkFlowId(),newFormData.getWorkFlowName(),newFormData.getWorkFlowActionId(),
            					newFormData.getWorkFlowActionName(),newFormData.getWorkFlowRemark());
            			
            			applicationWorkFlowService.saveApplicationWorkFlow(workFlow);
        				
        				message = messageSource.getMessage("general.processing.success", new Object[] { "Application Review" },currentLocale);
    					status = messageSource.getMessage("code.1001", null, currentLocale);
    					error = false;
    					
    				} else {
    					message = messageSource.getMessage("general.processing.failure", new Object[] { "Application Review" },currentLocale);
    					status = messageSource.getMessage("code.1002", null, currentLocale);
    					error = true;
    				}
            	} else {
					message = messageSource.getMessage("message.1007", null, currentLocale);
					status = messageSource.getMessage("code.1007", null, currentLocale);
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
     * Approve Form Data
     * @param data
     * @param dataPath
     * @param auth
     * @param request
     * @return ResponseEntity
     */
    @PostMapping("/approve/data/{path}")
    public ResponseEntity<?> processFormDataAppoveProcess(@RequestBody AppProcessingDto appProcessingDto,
    		@PathVariable("path") String dataPath,JwtAuthenticationToken auth,HttpServletRequest request) {
    	
    	try {
    		
    		AuthDetailsDto authDetails = utils.getTokenAuthenticationDetails(auth);
    		
    		if(dataPath.equals(Constants.DATA_PATH_1)) {
    			
    			Optional<FormData1> application = formDataService.findFormData1ByApplicationUid(
    					UUID.fromString(appProcessingDto.getApplicationUid()));
    			Date today = new Date();
    			if(application.isPresent()) {
    				/**
        			 * Approve Application
        			 */
    				if(appProcessingDto.getDecision().equals(Constants.DECISION_APPROVE)) {
    					application.get().setWorkFlowName(Constants.WORK_FLOW_BILLING);
        				application.get().setWorkFlowId(Constants.WORK_FLOW_BILLING_ID);
        				application.get().setWorkFlowActionName(Constants.ACTION_APPROVAL_ACCEPTED);
        				application.get().setWorkFlowActionId(Constants.ACTION_APPROVAL_ACCEPTED_ID);
    				} else {
    					application.get().setWorkFlowActionName(Constants.ACTION_APPROVAL_REJECTED);
        				application.get().setWorkFlowActionId(Constants.ACTION_APPROVAL_REJECTED_ID);
    				}
    				
    				application.get().setUpdatedBy(authDetails.getUserId());
    				application.get().setUpdatedByUserName(authDetails.getUserName());
    				application.get().setUpdatedDate(today);
    				application.get().setWorkFlowRemark(appProcessingDto.getRemark());
    				
    				FormData1 newFormData = formDataService.saveFormData1(application.get());
    				if(newFormData != null) {
    					
    					/**
            			 * Create Workflow
            			 */
            			ApplicationWorkFlow workFlow = new ApplicationWorkFlow(authDetails.getUserId(),authDetails.getUserName(),newFormData.getId(),
            					newFormData.getApplicationUid(),newFormData.getWorkFlowId(),newFormData.getWorkFlowName(),newFormData.getWorkFlowActionId(),
            					newFormData.getWorkFlowActionName(),newFormData.getWorkFlowRemark());
            			
            			applicationWorkFlowService.saveApplicationWorkFlow(workFlow);
        				
        				message = messageSource.getMessage("general.processing.success", new Object[] { "Application Approval" },currentLocale);
    					status = messageSource.getMessage("code.1001", null, currentLocale);
    					error = false;
    					
    				} else {
    					message = messageSource.getMessage("general.processing.failure", new Object[] { "Application Approval" },currentLocale);
    					status = messageSource.getMessage("code.1002", null, currentLocale);
    					error = true;
    				}
            	} else {
					message = messageSource.getMessage("message.1007", null, currentLocale);
					status = messageSource.getMessage("code.1007", null, currentLocale);
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
     * Generate Application Bill Request
     * @param data
     * @param dataPath
     * @param auth
     * @param request
     * @return ResponseEntity
     */
    @PostMapping("/generatebill/data/{path}")
    public ResponseEntity<?> processFormDataGenerateBillProcess(@RequestBody AppProcessingDto appProcessingDto,
    		@PathVariable("path") String dataPath,JwtAuthenticationToken auth,HttpServletRequest request) {
    	
    	try {
    		
    		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    		AuthDetailsDto authDetails = utils.getTokenAuthenticationDetails(auth);
    		
    		if(dataPath.equals(Constants.DATA_PATH_1)) {
    			
    			Optional<FormData1> application = formDataService.findFormData1ByApplicationUid(
    					UUID.fromString(appProcessingDto.getApplicationUid()));
    			Date today = new Date();
    			if(application.isPresent()) {
    				/**
        			 * GenerateBill 
        			 */
    				//Get applicant detals
    				Optional<Applicant> applicant = applicantService.findById(application.get().getApplicantId());
    				//Get costing details
    				List<AppliedServiceCosting> appliedServiceCostings = appliedServiceCostingService.
    						findByReferenceApplicationIdAndReferenceApplicationTableAndRecordStatusId(
    						application.get().getId(),Constants.FORM_DATA_1,Constants.RECORD_STATUS_ACTIVE);
    				
    				//Prepare invoice message
    				List<InvoiceSubmissionApiReqServiceDto> invoiceServiceDetailList = new ArrayList<>();
    				RevenueSource revenueSource = null;
    				String invoicedescription = null;
    				BigDecimal invoiceAmount = new BigDecimal("0.00");
    				String currencyCode = null;
    				Double exchangeRate = 1.0D;
    				Integer paymentPriority = 1;
    				for(AppliedServiceCosting appliedService : appliedServiceCostings) {
    					
    					invoiceServiceDetailList.add(new InvoiceSubmissionApiReqServiceDto(
    					appliedService.getRevenueSource().getServiceDepartment().getDepartmentCode(),
    					appliedService.getServiceTypeCode(),
    					String.valueOf(appliedService.getReferenceApplicationId()),
    					appliedService.getTotalCost(),paymentPriority,appliedService.getRevenueSource()));
    					
    					paymentPriority ++;
    					invoiceAmount = invoiceAmount.add(appliedService.getTotalCost());
    					currencyCode = appliedService.getCurrencyCode();
    					exchangeRate = appliedService.getExchangeRate();
    					revenueSource = appliedService.getRevenueSource();
    					invoicedescription = appliedService.getServiceTypeName();
    				}
    				InvoiceSubmissionApiReqServicesDto invoiceServices = new InvoiceSubmissionApiReqServicesDto(invoiceServiceDetailList);
    				
    				List<InvoiceSubmissionApiReqDetailDto> invoiceDetailList = new ArrayList<>();
    				String invoiceRequestId = utils.generateTokenNumberByUse(Constants.TOKEN_TYPE_REQUEST_ID);
    				String invoiceNumber = utils.generateTokenNumberByUse(Constants.TOKEN_TYPE_INVOICE_NUMBER);
    				String issueDate = LocalDateTime.now().format(dateTimeFormatter);
    				String expiryDate = LocalDateTime.now().plusDays(Constants.DEFAULT_INVOICE_DURATION).format(dateTimeFormatter);
    				Integer detailCount = 1;
    				String institutionCode = revenueSource.getServiceDepartment().getServiceInstitution().getInstitutionCode();
    				
    				InvoiceSubmissionApiReqDetailDto invoiceDetail = new InvoiceSubmissionApiReqDetailDto(
    						invoiceNumber,
    						institutionCode,
    						authDetails.getWorkStation(),
    						invoicedescription,
    						applicant.get().getIdentityNumber(),
    						applicant.get().getIdentityTypeId(),
    						applicant.get().getApplicantAccount(),
    						applicant.get().getApplicantName(),
    						applicant.get().getMobileNumber(),
    						applicant.get().getEmailAddress(),
    						issueDate,
    						expiryDate,
    						authDetails.getUserName(),
    						authDetails.getUserName(),
    						invoiceAmount,
    						invoiceAmount,
    						Constants.PAY_OPT_PRECISE_ID,
    						currencyCode,
    						exchangeRate,
    						Constants.PAY_PLAN_POSTPAID_ID,
    						invoiceServices,
    						authDetails.getUserId(),
    						application.get().getId(),
    						Constants.DATA_PATH_1,
    						application.get().getApplicationNumber()
    						);
    				invoiceDetailList.add(invoiceDetail);
    				InvoiceSubmissionApiReqDetailsDto apiReqDetails = new InvoiceSubmissionApiReqDetailsDto(invoiceDetailList);
    				InvoiceSubmissionApiReqHeaderDto apiReqHeader = new InvoiceSubmissionApiReqHeaderDto(
    						invoiceRequestId,Constants.INVOICE_CALLBACK,Constants.INVOICE_TYPE_SINGLE,detailCount,institutionCode);
    				
    				InvoiceSubmissionApiReqBodyDto invoiceSubmissionApiReqBody = new InvoiceSubmissionApiReqBodyDto(
    						apiReqHeader,apiReqDetails);
    				//Send request for bill
    				
    				String receivedTime = utils.getLocalDateTime(new Date()).format(dateTimeFormatter);
    				String processRequestId = utils.getToken();
    				Long apiVersionNumber = 1L;
    				
    				String routingKeySuffix = invoiceOutRoutingKey.substring(invoiceOutRoutingKey.indexOf("*")+1);
					String routingKey = routingKeyPrefix+"."+institutionCode+routingKeySuffix;
					Map<String,String> amqHeaders = new HashMap<String,String>();
					amqHeaders.put("receivedTime", receivedTime);
					amqHeaders.put("requestId", invoiceRequestId);
					amqHeaders.put("processRequestId", processRequestId);
					amqHeaders.put("clientCode",institutionCode);
					amqHeaders.put("clientKey", Constants.IRCS_CORE_CLIENT_KEY);
					amqHeaders.put("apiVersion",String.valueOf(apiVersionNumber));
					
					
					boolean saveStatus = invoiceRequestConsumerService.saveInvoiceRequestDetails(invoiceSubmissionApiReqBody, amqHeaders, receivedTime);
					if(saveStatus) {
						
						boolean publishStatus = utils.publishMsgToExchange(messageOutExchange,routingKey,invoiceSubmissionApiReqBody,amqHeaders);
	    				
	    				application.get().setWorkFlowActionName(Constants.ACTION_BILLING_INITIATED);
	    				application.get().setWorkFlowActionId(Constants.ACTION_BILLING_INITIATED_ID);
	    				application.get().setUpdatedBy(authDetails.getUserId());
	    				application.get().setUpdatedByUserName(authDetails.getUserName());
	    				application.get().setUpdatedDate(today);
	    				application.get().setWorkFlowRemark(Constants.ACTION_BILLING_INITIATED);
	    				
	    				FormData1 newFormData = formDataService.saveFormData1(application.get());
	    				if(newFormData != null) {
	    					
	    					/**
	            			 * Create Workflow
	            			 */
	            			ApplicationWorkFlow workFlow = new ApplicationWorkFlow(authDetails.getUserId(),authDetails.getUserName(),newFormData.getId(),
	            					newFormData.getApplicationUid(),newFormData.getWorkFlowId(),newFormData.getWorkFlowName(),newFormData.getWorkFlowActionId(),
	            					newFormData.getWorkFlowActionName(),newFormData.getWorkFlowRemark());
	            			
	            			applicationWorkFlowService.saveApplicationWorkFlow(workFlow);
	        				
	        				message = messageSource.getMessage("general.processing.success", new Object[] { "Invoice Request" },currentLocale);
	    					status = messageSource.getMessage("code.1001", null, currentLocale);
	    					error = false;
	    					
	    				} else {
	    					message = messageSource.getMessage("general.processing.failure", new Object[] { "Invoice Request" },currentLocale);
	    					status = messageSource.getMessage("code.1002", null, currentLocale);
	    					error = true;
	    				}
					} else {
						message = messageSource.getMessage("general.processing.failure", new Object[] { "Invoice Request" },currentLocale);
    					status = messageSource.getMessage("code.1002", null, currentLocale);
    					error = true;
					}
            	} else {
					message = messageSource.getMessage("message.1007", null, currentLocale);
					status = messageSource.getMessage("code.1007", null, currentLocale);
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
}


