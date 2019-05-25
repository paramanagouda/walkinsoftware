package com.ws.spring.cachedata;

import java.util.ArrayList;
import java.util.List;

import com.ws.spring.dto.UserOptBean;

public class CacheData {
	
	private static List<UserOptBean> otpGeneratedUserList = new ArrayList<>();

	public static List<UserOptBean> getOtpGeneratedUserList() {
		return otpGeneratedUserList;
	}

	public static void setOtpGeneratedUserList(List<UserOptBean> otpGeneratedUserList) {
		CacheData.otpGeneratedUserList = otpGeneratedUserList;
	}
	
	

}
