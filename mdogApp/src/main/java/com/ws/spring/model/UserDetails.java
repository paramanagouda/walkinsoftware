package com.ws.spring.model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

//Table - User
@Entity
@Table(name = "t_ws_user")
@EntityListeners(AuditingEntityListener.class)
public class UserDetails {

	@Id
	@GeneratedValue
	private Long id;

	private String fullName;

	private String userName;

	private String password;

	private String emailId;

	private String mobileNumber;

	// Bar code will be contain more info
	private String barcode;

	private String permnentMobileNumber;

	private int roleId;

	private int isActive;

	private String hashcode;

	private String imeiNum;

	@CreationTimestamp
	private LocalDateTime insertedDate;

	@UpdateTimestamp
	private LocalDateTime updatedDate;

	public UserDetails() {

	}

	public UserDetails(Long id, String fullName, String userName, String password, String emailId, String mobileNumber,
			String barcode, String permnentMobileNumber, int roleId, int isActive, String hashcode, String imeiNum,
			LocalDateTime insertedDate, LocalDateTime updatedDate) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.userName = userName;
		this.password = password;
		this.emailId = emailId;
		this.mobileNumber = mobileNumber;
		this.barcode = barcode;
		this.permnentMobileNumber = permnentMobileNumber;
		this.roleId = roleId;
		this.isActive = isActive;
		this.hashcode = hashcode;
		this.imeiNum = imeiNum;
		this.insertedDate = insertedDate;
		this.updatedDate = updatedDate;
	}

	@Override
	public String toString() {
		return String.format(
				"UserDetails [id=%s, fullName=%s, userName=%s, password=%s, emailId=%s, mobileNumber=%s, barcode=%s, permnentMobileNumber=%s, roleId=%s, isActive=%s, hashcode=%s, imeiNum=%s, insertedDate=%s, updatedDate=%s]",
				id, fullName, userName, password, emailId, mobileNumber, barcode, permnentMobileNumber, roleId,
				isActive, hashcode, imeiNum, insertedDate, updatedDate);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getPermnentMobileNumber() {
		return permnentMobileNumber;
	}

	public void setPermnentMobileNumber(String permnentMobileNumber) {
		this.permnentMobileNumber = permnentMobileNumber;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getHashcode() {
		return hashcode;
	}

	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}

	public String getImeiNum() {
		return imeiNum;
	}

	public void setImeiNum(String imeiNum) {
		this.imeiNum = imeiNum;
	}

	public LocalDateTime getInsertedDate() {
		return insertedDate;
	}

	public void setInsertedDate(LocalDateTime insertedDate) {
		this.insertedDate = insertedDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}	
}
