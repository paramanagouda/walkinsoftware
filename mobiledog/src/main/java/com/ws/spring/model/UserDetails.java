package com.ws.spring.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

//Table - User
@Entity
@Table(name = "t_ws_user")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(description = "All details about the User")
public class UserDetails implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@ApiModelProperty(notes = "The database generated userDetails ID")
	private Long id;

	@ApiModelProperty(notes = "The user full name")
	private String fullName;

	@ApiModelProperty(notes = "The user Name used for login")
	private String userName;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@ApiModelProperty(notes = "The employee email id")
	private String emailId;

	private String mobileNumber;

	private String otp;

	private String mpin;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String barcode;

	private String secondaryMobileNumber;

	@OneToOne
	private Role role;

	private int isActive;

	private int approveStatus;

	private Long approvedBy;

	private String reason;

	private String imeiNum;
	
	private Boolean gpsTracking;
	
	private Boolean emergency;

	@CreationTimestamp
	private LocalDateTime insertedDate;

	@UpdateTimestamp
	private LocalDateTime updatedDate;

	public UserDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UserDetails(Long id, String fullName, String userName, String password, String emailId, String mobileNumber,
			String otp, String mpin, String barcode, String secondaryMobileNumber, Role role, int isActive,
			int approveStatus, Long approvedBy, String reason, String imeiNum, Boolean gpsTracking, Boolean emergency,
			LocalDateTime insertedDate, LocalDateTime updatedDate) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.userName = userName;
		this.password = password;
		this.emailId = emailId;
		this.mobileNumber = mobileNumber;
		this.otp = otp;
		this.mpin = mpin;
		this.barcode = barcode;
		this.secondaryMobileNumber = secondaryMobileNumber;
		this.role = role;
		this.isActive = isActive;
		this.approveStatus = approveStatus;
		this.approvedBy = approvedBy;
		this.reason = reason;
		this.imeiNum = imeiNum;
		this.gpsTracking = gpsTracking;
		this.emergency = emergency;
		this.insertedDate = insertedDate;
		this.updatedDate = updatedDate;
	}


	@Override
	public String toString() {
		return "UserDetails [id=" + id + ", fullName=" + fullName + ", userName=" + userName + ", password=" + password
				+ ", emailId=" + emailId + ", mobileNumber=" + mobileNumber + ", otp=" + otp + ", mpin=" + mpin
				+ ", barcode=" + barcode + ", secondaryMobileNumber=" + secondaryMobileNumber + ", role=" + role
				+ ", isActive=" + isActive + ", approveStatus=" + approveStatus + ", approvedBy=" + approvedBy
				+ ", reason=" + reason + ", imeiNum=" + imeiNum + ", gpsTracking=" + gpsTracking + ", emergency="
				+ emergency + ", insertedDate=" + insertedDate + ", updatedDate=" + updatedDate + "]";
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

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getSecondaryMobileNumber() {
		return secondaryMobileNumber;
	}

	public void setSecondaryMobileNumber(String secondaryMobileNumber) {
		this.secondaryMobileNumber = secondaryMobileNumber;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public int getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(int approveStatus) {
		this.approveStatus = approveStatus;
	}

	public Long getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(Long approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getImeiNum() {
		return imeiNum;
	}

	public void setImeiNum(String imeiNum) {
		this.imeiNum = imeiNum;
	}

	public Boolean getGpsTracking() {
		return gpsTracking;
	}

	public void setGpsTracking(Boolean gpsTracking) {
		this.gpsTracking = gpsTracking;
	}

	public Boolean getEmergency() {
		return emergency;
	}

	public void setEmergency(Boolean emergency) {
		this.emergency = emergency;
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
