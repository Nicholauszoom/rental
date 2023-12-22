package com.ircs.portal.util;

import org.springframework.stereotype.Component;

@Component
public class Constants {
	
	public static final Integer MAX_LOGIN_ATTEMPTS = 5;
	public static final Long USER_ACCOUNT_AUTOLOCK_TIME = 300L;

	//Status
	public static final Long RECORD_STATUS_ACTIVE = 1L;
	public static final Long RECORD_STATUS_INACTIVE = 0L;
}
