package com.ws.app.cachedata;

import java.util.HashMap;
import java.util.Map;

import com.ws.common.util.StringUtil;
import com.ws.spring.dto.UserOtpBean;

public class CacheData {

	private static Map<String, UserOtpBean> otpGeneratedUserList = new HashMap<String, UserOtpBean>();

	public static void addToCache(UserOtpBean userOtpBean) {
		String userName = userOtpBean.getUserName();
		if (StringUtil.checkNullOrEmpty(userName)) {
			userOtpBean.setUserName(userOtpBean.getMobileNumber());
		}
		otpGeneratedUserList.put(userOtpBean.getMobileNumber(), userOtpBean);
	}

	public static UserOtpBean getUserOtpBean(String mobileNumber) {
		UserOtpBean userOtpBean = otpGeneratedUserList.get(mobileNumber);
		if (null == userOtpBean) {
			for (Map.Entry<String, UserOtpBean> entry : otpGeneratedUserList.entrySet()) {
				UserOtpBean bean = entry.getValue();
				if (mobileNumber.equals(bean.getMobileNumber()) || mobileNumber.equals(bean.getUserName())
						|| mobileNumber.equals(bean.getEmailId())) {
					userOtpBean = bean;
					break;
				}
			}
		}
		return userOtpBean;
	}

	public static void removeFromCache(UserOtpBean userOtpBean) {
		otpGeneratedUserList.remove(userOtpBean.getMobileNumber());
	}

}
