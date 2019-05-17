package com.ws.spring.util;

import com.ws.spring.exception.ClientResponseBean;

public class ClientResponseUtil {

	public static ClientResponseBean getSuccessResponse() {
		return new ClientResponseBean("SUCCESS", "0000", "Activity success");
	}

	public static ClientResponseBean getErrorResponse() {
		return new ClientResponseBean("VAlidationFailed", "00001", "user values are not proper");
	}

}
