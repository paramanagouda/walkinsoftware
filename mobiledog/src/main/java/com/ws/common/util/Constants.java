package com.ws.common.util;

public interface Constants {

	/**
	 * User Role description
	 */
	 int ROLE_ID_SUPERADMIN = 1;
	 int ROLE_ID_ADMIN = 2;
	 int ROLE_ID_USER = 3;
	
	/**
	 * isActive parameter descriptions
	 */
	 int REGISTERED = 0;
	 int ACTIVE = 1;
	 int BLOCKED = 2;
	
	/** The Constant BLANK. */
	 String BLANK = "";
	
	/** The Constant COLON. */
	 String COLON = " : ";
	
	/** The Constant DASH. */
	 String DASH = " - ";
	
	/** The Constant KEY_TXNID. */
	 String KEY_TXNID = "TXNID";
	
	/** The Constant XPATH_TXNID. */
	 String XPATH_TXNID = "//transcationId";
	
	/** The Constant ENTRY. */
	 String ENTRY = "Entry";
	
	/** The Constant EXIT. */
	 String EXIT = "Exit";
	
	/** COnstant int numbers */
	 int INT_SIX = 6;
	
	 String REGISTRATION_STR = "REGISTRATION";
	 
	 String LOGIN_BY_MPIN = "LOGIN_BY_MPIN";
	 
	 String LOGIN_BY_OTP = "LOGIN_BY_OTP";
	 
	 String LOGIN_BY_PASSWORD = "LOGIN_BY_PASSWORD";
}
