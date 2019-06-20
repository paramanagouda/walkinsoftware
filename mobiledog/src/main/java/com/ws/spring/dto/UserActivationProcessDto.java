package com.ws.spring.dto;

public class UserActivationProcessDto {

	private Long[] userIds;

	private String operationType;

	private String reason;

	public UserActivationProcessDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserActivationProcessDto(Long[] userIds, String operationType, String reason) {
		super();
		this.userIds = userIds;
		this.operationType = operationType;
		this.reason = reason;
	}

	public Long[] getUserIds() {
		return userIds;
	}

	public void setUserIds(Long[] userIds) {
		this.userIds = userIds;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return String.format("UserActivationProcessDto [userIds=%s, operationType=%s, reason=%s]", userIds,
				operationType, reason);
	}

}
