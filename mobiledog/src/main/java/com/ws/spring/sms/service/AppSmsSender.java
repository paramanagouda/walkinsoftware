package com.ws.spring.sms.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.ws.common.util.StringUtil;
import com.ws.spring.dto.UserOtpBean;
import com.ws.spring.model.UserDetails;

@Service
@PropertySource("classpath:smsconfig.properties")
public class AppSmsSender {

	@Autowired
	private Environment env;

	@Autowired
	SendSms sendSms;

	// private String smsMainUrl =
	// "http://smshorizon.co.in/api/sendsms.php?user={{username}}&apikey={{apikey}}&message={{message}}&mobile={{mobile}}&senderid={{senderid}}&type={{type}}";
	private String smsMainUrl;

	Logger logger = LogManager.getLogger(this.getClass().getName());

	public AppSmsSender() {
	}


	public void sendUserRegistrationSms(UserDetails userDetails) {
		String mainUrl = prepareSms(userDetails, "mdogapp.sms.user.registration");
		sendSms.sendSmstoUser(mainUrl, userDetails.getMobileNumber());

	}

	public void sendUserOtp(UserOtpBean userOtpBean) {
		Map<String, String> mainReplacements = new HashMap<String, String>();
		mainReplacements.put("username", env.getProperty("mdogapp.sms.username"));
		mainReplacements.put("apikey", env.getProperty("mdogapp.sms.apikey"));
		mainReplacements.put("senderid", env.getProperty("mdogapp.sms.senderid"));
		mainReplacements.put("type", env.getProperty("mdogapp.sms.type"));

		smsMainUrl = StringUtil.messageFormat(env.getProperty("mdogapp.sms.mainUrl"), mainReplacements);
		
		Map<String, String> replacements = new HashMap<String, String>();
		//replacements.put("user", userOtpBean.getUserName());
		//replacements.put("username", userOtpBean.getUserName());
		replacements.put("otp", userOtpBean.getOtp());
		String otpMessage = env.getProperty("mdogapp.sms.user.otp");
		String message = StringUtil.messageFormat(otpMessage, replacements);

		Map<String, String> mainUrlreplacements = new HashMap<String, String>();
		String messageEncoded = StringUtil.encode(message);
		logger.info("Message : {} , String util ENcoder {}", message, messageEncoded);
		mainUrlreplacements.put("message", messageEncoded);
		String mobileNumber = userOtpBean.getMobileNumber();
		mainUrlreplacements.put("mobile", mobileNumber);
		String mainUrl = StringUtil.messageFormat(smsMainUrl, mainUrlreplacements);
		sendSms.sendSmstoUser(mainUrl, userOtpBean.getMobileNumber());
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
		String messageEncoded = StringUtil.encode(message);
		logger.info("Message : {} , String util ENcoder {}", message, messageEncoded);

		mainUrlreplacements.put("message", messageEncoded);
		String mobileNumber = userDetails.getMobileNumber();
		mainUrlreplacements.put("mobile", mobileNumber);
		return StringUtil.messageFormat(smsMainUrl, mainUrlreplacements);
	}
}
