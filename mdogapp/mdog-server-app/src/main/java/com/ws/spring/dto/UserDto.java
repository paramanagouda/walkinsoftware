package com.ws.spring.dto;

import java.io.Serializable;
import java.util.Date;

public class UserDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4618329720480093775L;

	private String username;

	private String isOptVerified;

	private String password;
	
	private String currentPassword;
	
	private String newPassword;
	
	private Date currentTime;

	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserDto(String username, String isOptVerified, String password, String currentPassword, String newPassword,
			Date currentTime) {
		super();
		this.username = username;
		this.isOptVerified = isOptVerified;
		this.password = password;
		this.currentPassword = currentPassword;
		this.newPassword = newPassword;
		this.currentTime = currentTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public Date getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	@Override
	public String toString() {
		return String.format(
				"UserDto [username=%s, isOptVerified=%s, password=%s, currentPassword=%s, newPassword=%s, currentTime=%s]",
				username, isOptVerified, password, currentPassword, newPassword, currentTime);
	}
}
