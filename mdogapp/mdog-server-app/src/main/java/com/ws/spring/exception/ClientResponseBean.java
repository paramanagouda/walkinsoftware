package com.ws.spring.exception;

public class ClientResponseBean {

	private int responseCode;
	private String status;
	private String message;
	private String errorMessage;
	private Exception exception;

	public ClientResponseBean(int responseCode, String status, String message, String errorMessage) {
		super();
		this.responseCode = responseCode;
		this.status = status;
		this.message = message;
		this.errorMessage = errorMessage;
	}

	public ClientResponseBean(int responseCode, String status, String message, String errorMessage, String descption,
			Exception exeption) {
		super();
		this.responseCode = responseCode;
		this.status = status;
		this.message = message;
		this.errorMessage = errorMessage;
		this.exception = exeption;

	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}
}
