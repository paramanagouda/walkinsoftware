package com.ws.app.cachedata;

import java.util.ArrayList;
import java.util.List;

import com.ws.spring.dto.UserOtpBean;

public class CacheData {
	
	private static List<UserOtpBean> otpGeneratedUserList = new ArrayList<>();

	public static List<UserOtpBean> getOtpGeneratedUserList() {
		return otpGeneratedUserList;
	}

	public static void setOtpGeneratedUserList(List<UserOtpBean> otpGeneratedUserList) {
		CacheData.otpGeneratedUserList = otpGeneratedUserList;
	}
	
	

}
