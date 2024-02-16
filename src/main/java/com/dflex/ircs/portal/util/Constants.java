package com.dflex.ircs.portal.util;

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
	public static final UUID DEFAULT_SYS_USERID = UUID.fromString("c826cde7-fbff-424d-9105-814c31a9b551");
	public static final String DEFAULT_SYS_USERNAME = "Admin";
	public static final String SERVICE_INSTITUTION_PREFIX = "SI";
	public static final String PAYMENT_FACILITATOR_PREFIX = "PF";
	public static final String SERVICE_OTHER_INSTITUTION_PREFIX = "OSI";
	public static final Long CLIENT_CATEGORY_SERVICE_INSTITUTION = 1L;
	public static final Long CLIENT_CATEGORY_PAYMENT_FACILITATOR= 2L;
	public static final Long CLIENT_CATEGORY_OTHER_SERVICE_INSTITUTION = 3L;
	
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
	
}


