package com.ws.spring.dto;

import java.io.Serializable;
import java.util.Date;

public class UserDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4618329720480093775L;

	private String username;
	
	private String mobileNumber;
	
	private String emailId;

	private String isOptVerified;

	private String password;

	private String currentPassword;

	private String newPassword;

	private String otp;

	private String mpin;

	private Date currentTime;

	public UserDto() {
		super();
	}

	public UserDto(String username, String mobileNumber, String emailId, String isOptVerified, String password,
			String currentPassword, String newPassword, String otp, String mpin, Date currentTime) {
		super();
		this.username = username;
		this.mobileNumber = mobileNumber;
		this.emailId = emailId;
		this.isOptVerified = isOptVerified;
		this.password = password;
		this.currentPassword = currentPassword;
		this.newPassword = newPassword;
		this.otp = otp;
		this.mpin = mpin;
		this.currentTime = currentTime;
	}

	@Override
	public String toString() {
		return String.format(
				"UserDto [username=%s, mobileNumber=%s, emailId=%s, isOptVerified=%s, password=%s, currentPassword=%s, newPassword=%s, otp=%s, mpin=%s, currentTime=%s]",
				username, mobileNumber, emailId, isOptVerified, password, currentPassword, newPassword, otp, mpin,
				currentTime);
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getIsOptVerified() {
		return isOptVerified;
	}

	public void setIsOptVerified(String isOptVerified) {
		this.isOptVerified = isOptVerified;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getMpin() {
		return mpin;
	}

	public void setMpin(String mpin) {
		this.mpin = mpin;
	}

	public Date getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
