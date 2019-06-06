package com.ws.spring.sms.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.ws.spring.dto.UserOtpBean;
import com.ws.spring.model.UserDetails;
import com.ws.spring.util.StringUtil;

@Component
@PropertySource("file:smsconfig.properties")

public class AppSmsSender {

	@Autowired
	private Environment env;

	@Autowired
	SendSms sendSms;

	// private String smsMainUrl =
	// "http://smshorizon.co.in/api/sendsms.php?user={{username}}&apikey={{apikey}}&message={{message}}&mobile={{mobile}}&senderid={{sendid}}&type={{type}}";
	private String smsMainUrl;

	public AppSmsSender() {
		Map<String, String> replacements = new HashMap<String, String>();
		replacements.put("username", env.getProperty("mdogapp.sms.username"));
		replacements.put("apikey", env.getProperty("mdogapp.sms.apikey"));
		replacements.put("senderid", env.getProperty("mdogapp.sms.senderid"));
		replacements.put("type", env.getProperty("mdogapp.sms.type"));

		this.smsMainUrl = StringUtil.messageFormat(env.getProperty("mdogapp.sms.mainUrl"), replacements);
	}

	public void sendUserRegistrationSms(UserDetails userDetails) {
		String mainUrl = prepareSms(userDetails, "mdogapp.sms.user.registration");
		sendSms.sendSmstoUser(mainUrl, userDetails.getMobileNumber());

	}

	public void sendUserOtp(UserOtpBean userDetails) {
		Map<String, String> replacements = new HashMap<String, String>();
		replacements.put("user", userDetails.getUserName());
		replacements.put("username", userDetails.getUserName());
		replacements.put("otp", userDetails.getOtp());
		String message = StringUtil.messageFormat(env.getProperty("mdogapp.sms.user.otp"), replacements);

		Map<String, String> mainUrlreplacements = new HashMap<String, String>();
		mainUrlreplacements.put("message", message);
		String mobileNumber = userDetails.getMobileNumber();
		mainUrlreplacements.put("mobile", mobileNumber);
		String mainUrl= StringUtil.messageFormat(smsMainUrl, mainUrlreplacements);
		sendSms.sendSmstoUser(mainUrl, userDetails.getMobileNumber());
	}

	private String prepareSms(UserDetails userDetails, String msgCode) {
		Map<String, String> replacements = new HashMap<String, String>();
		replacements.put("fullname", userDetails.getFullName());
		replacements.put("user", userDetails.getUserName());
		replacements.put("password", userDetails.getPassword());
		replacements.put("username", userDetails.getUserName());
		replacements.put("otp", userDetails.getOtp());
		String message = StringUtil.messageFormat(env.getProperty(msgCode), replacements);

		Map<String, String> mainUrlreplacements = new HashMap<String, String>();
		mainUrlreplacements.put("message", message);
		String mobileNumber = userDetails.getMobileNumber();
		mainUrlreplacements.put("mobile", mobileNumber);
		return StringUtil.messageFormat(smsMainUrl, mainUrlreplacements);

	}
}
