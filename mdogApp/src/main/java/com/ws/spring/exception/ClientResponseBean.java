package com.ws.spring.exception;

public class ClientResponseBean {

	private String message;
	private String responseCode;
	private String descption;
	
	public ClientResponseBean(String message, String responseCode, String descption) {
		super();
		this.message = message;
		this.responseCode = responseCode;
		this.descption = descption;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getDescption() {
		return descption;
	}

	public void setDescption(String descption) {
		this.descption = descption;
	}
	
	
}
