package com.dflex.ircs.portal.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class Constants {
	
	public static final Integer MAX_LOGIN_ATTEMPTS = 5;
	public static final Long USER_ACCOUNT_AUTOLOCK_TIME = 300L;
	public static final Integer MAX_SMS_CONFIRMATION_TOKEN_COUNT = 3;
	public static final Integer MAX_EMAIL_CONFIRMATION_TOKEN_COUNT = 3;

	//Status
	public static final Long RECORD_STATUS_ACTIVE = 1L;
	public static final Long RECORD_STATUS_INACTIVE = 0L;
	
	//Spring Security
	public static final String  CLIENT_ID_PORTAL =  "portal";
	public static final String  CLIENT_SECRET_PORTAL =  "portal321";
	public static final String  CLIENT_ID_MOBILE =  "mobile";
	public static final String  CLIENT_SECRET_MOBILE =  "mobile321";
	public static final String  OAUTH2_AUTHORIZE_URL =  "/oauth2/authorize";
	public static final String  OAUTH2_AUTHORIZE_REDIRECT_URL =  "/oauth2/authorized";
	public static final String  OAUTH2_ACCESS_TOKEN_URL =  "/oauth2/token";
	public static final String  OAUTH2_CHALLENGE_METHOD =  "S256";
	
	//Response Codes
	public static final String DEFAULT_SUCCESS = "1001";
	public static final String DEFAULT_FAILURE = "1002";
	public static final String  INVALID_CLIENT_KEY="1003";
    public static final String  INACTIVE_OR_INVALID_CLIENT = "1013";
	public static final String INACTIVE_OR_DISABLED_CLIENT_CONFIGURATION ="1012";

	public static String CLIENT_SYSTEM_CONFIGURATION_NOT_FOUND="1011";

	public static final String INVOICE_NOTFOUND= "1028";
	
	//User Category
	public static final Long USER_CATEGORY_OFFICER = 1L;
	public static final Long USER_CATEGORY_CUSTOMER = 2L;
	
	//Email Types
	public static final String MAIL_TYPE_EMAIL_VERIFICATION = "EMAIL_VERIFICATION";
	public static final String MAIL_TYPE_PASSWORD_RESET_LINK = "EMAIL_PASSWORD_RESET";
	public static final String MAIL_TYPE_USER_CREDENTIALS = "EMAIL_USER_CREDENTIALS";
	
	//Other
	public static final Long DEFAULT_CUSTOMER_USER_ROLE = 1L;
	public static final UUID DEFAULT_SYS_USERID = UUID.fromString("c4ba1274-dfc1-40d5-8bd9-734c0c3d1980");
	public static final String DEFAULT_SYS_USERNAME = "IRCS Portal Admin";
	public static final String SERVICE_INSTITUTION_PREFIX = "SI";
	public static final String PAYMENT_FACILITATOR_PREFIX = "PF";
	public static final String SERVICE_OTHER_INSTITUTION_PREFIX = "OSI";
	public static final Long CLIENT_CATEGORY_SERVICE_INSTITUTION = 1L;
	public static final Long CLIENT_CATEGORY_PAYMENT_FACILITATOR= 2L;
	public static final Long CLIENT_CATEGORY_OTHER_SERVICE_INSTITUTION = 3L;
	public static final Long  DEFAULT_INVOICE_DURATION = 7L;
	public static final String PORTAL_CLIENT_KEY = "NzQ3ODJmNTEtODczMi00OGMwLWI0YTMtYjM2NjJmYmVlNzQz";
	public static final BigDecimal DEFAULT_MIN_AMOUNT_PAYABLE = new BigDecimal(0.01);
	public static final String DEFAULT_SUCCESS_STATUS = "1001";
	public static final String TRANSACTION_TYPE_CODE_CREDIT = "CR";
	public static final String TRANSACTION_TYPE_CODE_DEBIT = "DR";
	
	public static final String IRCS_CORE_CODE = "OSI1001";
	public static final String IRCS_CORE_CLIENT_KEY = "MDJlZGY4MDMtMGY2MS00MDU1LWEzNTEtZWVmNjZmYjM3ZDZk";
	
	//Log Levels
	public final static Integer LOG_DEBUG = 1;
	public final static Integer LOG_INFO = 2;
	public final static Integer LOG_WARN = 3;
	public final static Integer LOG_ERROR = 4;
	public final static Integer LOG_FATAL = 5;
	
	//Request Ip Headers
	public static final String[] IP_HEADERS = {
	        "X-Forwarded-For",
	        "Proxy-Client-IP",
	        "WL-Proxy-Client-IP",
	        "HTTP_X_FORWARDED_FOR",
	        "HTTP_X_FORWARDED",
	        "HTTP_X_CLUSTER_CLIENT_IP",
	        "HTTP_CLIENT_IP",
	        "HTTP_FORWARDED_FOR",
	        "HTTP_FORWARDED",
	        "HTTP_VIA",
	        "REMOTE_ADDR"
	};
	
	//Api Categories
	public final static Long API_CATEG_INVOICE_SUBMISSION_REQ = 1L;
	public final static Long API_CATEG_INVOICE_SUBMISSION_RES = 2L;
	public final static Long API_CATEG_INVOICE_VALIDATION_REQ = 3L;
	public final static Long API_CATEG_PAYMENT_VALIDATION_REQ = 5L;
	public final static Long API_CATEG_SI_PAYMENT_NOTIFICATION = 6L;
	
	//Api Categories
	public final static Long API_CATEG_INVOICE_SUBMISSION = 1L;
	public final static Long API_CATEG_INVOICE_NOTIFICATION = 2L;
	public final static Long API_CATEG_INVOICE_VALIDATION = 3L;
	public final static Long API_CATEG_RECON_SUBMISSSION = 7L;
	
	
	//Service Type Level
	public final static Integer SERVICE_TYPE_LEVEL_1 = 1;
	public final static Integer SERVICE_TYPE_LEVEL_2 = 2;
	
	//Data Table and Fields
	public static final String DATA_PATH_1 = "data1";
	public static final String DATA_PATH_2 = "data2";
	
	public static final String FORM_DATA_1 = "tab_form_data1";
	public static final String FORM_DATA_2 = "tab_form_data2";
	
	public static final List<String> DATA_LIST_DEFAULT_FIELDS = Arrays.asList(
			"application_uid","created_date","app_form_uid","work_flow_id","work_flow_name","work_flow_action_id","work_flow_action_name"
			);
	
	public static final List<String> DATA_DETAIL_DEFAULT_FIELDS = Arrays.asList(
			"application_uid","app_form_uid","work_flow_id","work_flow_name","work_flow_action_id","work_flow_action_name","has_attachment"
			);
	public static final List<String> DATA_DETAIL_DEFAULT_FIELDS_LABELS = Arrays.asList(
			"applicationUid","formUid","workFlowId","Work Flow","workFlowActionId","Work Flow Action","hasAttachment"
			);
	
	public static final Long DATA_SOURCE_TYPE_INPUT = 1L;
	public static final Long DATA_SOURCE_TYPE_LOOKUP = 2L;
	
	
	//Work Flows
	public static final String WORK_FLOW_APPLICATION = "Application";
	public static final Long WORK_FLOW_APPLICATION_ID = 1L;
	public static final String WORK_FLOW_REVIEW = "Review";
	public static final Long WORK_FLOW_REVIEW_ID = 2L;
	public static final String WORK_FLOW_APPROVAL = "Approval";
	public static final Long WORK_FLOW_APPROVAL_ID = 3L;
	public static final String WORK_FLOW_BILLING = "Billing";
	public static final Long WORK_FLOW_BILLING_ID = 4L;
	public static final String WORK_FLOW_PAYMENT = "Payment";
	public static final Long WORK_FLOW_PAYMENT_ID = 5L;
	
	//Work Flow Actions
	public static final String ACTION_APPLICATION_INITIATED = "Application Initiated";
	public static final Long ACTION_APPLICATION_INITIATED_ID = 1L;
	public static final String ACTION_APPLICATION_SUBMITTED = "Application Submitted";
	public static final Long ACTION_APPLICATION_SUBMITTED_ID = 2L;
	public static final String ACTION_APPLICATION_ASSIGNED = "Application Assignment";
	public static final Long ACTION_APPLICATION_ASSIGNED_ID = 3L;
	public static final String ACTION_REVIEW_ACCEPTED = "Review Accepted";
	public static final Long ACTION_REVIEW_ACCEPTED_ID = 4L;
	public static final String ACTION_REVIEW_REJECTED = "Review Rejected";
	public static final Long ACTION_REVIEW_REJECTED_ID = 5L;
	public static final String ACTION_APPROVAL_ACCEPTED = "Approval Accepted";
	public static final Long ACTION_APPROVAL_ACCEPTED_ID = 6L;
	public static final String ACTION_APPROVAL_REJECTED = "Approval Rejected";
	public static final Long ACTION_APPROVAL_REJECTED_ID = 7L;
	public static final String ACTION_BILLING_INITIATED = "Billing Initiated";
	public static final Long ACTION_BILLING_INITIATED_ID = 8L;
	public static final String ACTION_BILLING_COMPLETED = "Billing Completed";
	public static final Long ACTION_BILLING_COMPLETED_ID = 9L;
	public static final String ACTION_PAYMENT_ONPROGRESS = "Payment On-Progress";
	public static final Long ACTION_PAYMENT_ONPROGRESS_ID = 10L;
	public static final String ACTION_PAYMENT_PAID = "Paid";
	public static final Long ACTION_PAYMENT_PAID_ID = 11L;
	
	//Decision
	public static final String DECISION_ACCEPT = "ACCEPTED";
	public static final String DECISION_REJECT = "REJECTED";
	public static final String DECISION_APPROVE = "APPROVED";
	
	//Token Types
	public static final Long TOKEN_TYPE_INVOICE_NUMBER = 1L;
	public static final Long TOKEN_TYPE_REQUEST_ID = 2L;
	
	//Payment Options
	public static final Long PAY_OPT_COMPLETE_ID = 1L;
	public static final String PAY_OPT_COMPLETE = "COMPLETE";
	public static final Long PAY_OPT_PARTIAL_ID = 2L;
	public static final String PAY_OPT_PARTIAL = "PARTIAL";
	public static final Long PAY_OPT_PRECISE_ID = 3L;
	public static final String PAY_OPT_PRECISE = "PRECISE";
	public static final Long PAY_OPT_LIMITED_ID = 4L;
	public static final String PAY_OPT_LIMITED = "LIMITED";
	public static final Long PAY_OPT_PERPETUAL_ID = 5L;
	public static final String PAY_OPT_PERPETUAL = "PERPETUAL";
	
	//Payment Plan
	public static final Long PAY_PLAN_PREPAID_ID = 1L;
	public static final String PAY_PLAN_PREPAID = "PRE-PAID";
	public static final Long PAY_PLAN_POSTPAID_ID = 2L;
	public static final String PAY_PLAN_POSTPAID = "POST-PAID";
	
	//Urls
	public static final String INVOICE_CALLBACK = "https://uat.ircsdigital.com:8443/api/invoice/submit/response-v1";
	
	//Invoice Types
	public static final Long INVOICE_TYPE_SINGLE = 1L;
	public static final Long INVOICE_TYPE_MULTIPLE = 2L;

	
	//Signature Algorithm
	public static final Long SIG_ALG_SHA1 = 1L;
	public static final Long SIG_ALG_SHA2 = 2L;
	
	//Connection Times
	public final static Long CONNECT_TIMEOUT = 10000L;
	public final static Long RETRY_CONNECT_TIMEOUT = 30000L;
	
	//Reminder Status
	public static final Long REMINDER_STATUS_OFF = 0L;
	public static final Long REMINDER_STATUS_ON = 1L;
	public static final Long REMINDER_STATUS_NOT_APPLICABLE = 2L;
	
	//Payment Status
	public static final String PAYMENT_NOT_PAID = "NOT PAID";
	public static final String PAYMENT_PAID_PARTLY = "PARTIALLY PAID";
	public static final String PAYMENT_PAID = "PAID";
}


