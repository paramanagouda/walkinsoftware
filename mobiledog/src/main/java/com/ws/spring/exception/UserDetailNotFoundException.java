package com.ws.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Actor Not Found")
public class UserDetailNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	public UserDetailNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
