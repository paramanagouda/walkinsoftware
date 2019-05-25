package com.ws.spring.exception;

public class ClientResponseBean<T> {

	private int responseCode;
	private String message;
	private String descption;
	private Object result;

	public ClientResponseBean(int responseCode, String message, String descption, Object result) {
		super();
		this.responseCode = responseCode;
		this.message = message;
		this.descption = descption;
		this.result = result;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDescption() {
		return descption;
	}

	public void setDescption(String descption) {
		this.descption = descption;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}
