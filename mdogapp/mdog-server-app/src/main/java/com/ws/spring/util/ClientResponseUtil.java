package com.ws.spring.util;

import com.ws.spring.exception.ClientResponseBean;

public class ClientResponseUtil {

	public static ClientResponseBean getSuccessResponse() {
		return new ClientResponseBean(0000,"SUCCESS", "Activity success",null);
	}

	public static ClientResponseBean getErrorResponse() {
		return new ClientResponseBean(00001,"ValidationFailed", "user values are not proper", null);
	}

}
