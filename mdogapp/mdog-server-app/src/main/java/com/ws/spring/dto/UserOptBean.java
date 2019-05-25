package com.ws.spring.dto;

import java.io.Serializable;
import java.util.Date;

public class UserOptBean implements Serializable{

	private String userName;

	private String emailId;

	private String opt;

	private Date generatedTime;

	public UserOptBean() {

	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public UserOptBean(String userName, String emailId, String opt, Date generatedTime) {
		super();
		this.userName = userName;
		this.emailId = emailId;
		this.opt = opt;
		this.generatedTime = generatedTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	public Date getGeneratedTime() {
		return generatedTime;
	}

	public void setGeneratedTime(Date generatedTime) {
		this.generatedTime = generatedTime;
	}

}
