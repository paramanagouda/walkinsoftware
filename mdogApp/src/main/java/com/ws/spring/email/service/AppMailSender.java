package com.ws.spring.email.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ws.spring.cachedata.CacheData;
import com.ws.spring.dto.UserOptBean;
import com.ws.spring.model.UserDetails;
import com.ws.spring.util.Constants;
import com.ws.spring.util.StringUtil;

@Component
public class AppMailSender {

	private static final Logger logger = LoggerFactory.getLogger(AppMailSender.class);

	@Autowired
	EmailServiceImpl emailServiceImpl;

	private EmailTemplate resistrationConformationEmailTemplate;
	private EmailTemplate optVerificationEmailTemplate;
	private EmailTemplate changePasswordEmailTemplate;

	private String url = "http://localhost:8080/home/v1/verifyHashCode?userName={userName}&hashCode={hashCode}";

	public void sendRegistrationConfirmationMail(UserDetails userDetails) {
		logger.info("User {} RegistrationCOnfiration mail Sending process Start.", userDetails.getUserName());
		if (null == resistrationConformationEmailTemplate) {
			resistrationConformationEmailTemplate = new EmailTemplate("registrationConfirmation.html");
		}

		Map<String, String> replacements = new HashMap<String, String>();
		replacements.put("user", userDetails.getFullName());
		replacements.put("today", String.valueOf(userDetails.getInsertedDate()));
		logger.info("HAshCode Generated : {}", userDetails.getHashcode());
		String hashcode = StringUtil.encode(userDetails.getHashcode());
		logger.info("HashCode after encoding : {}", hashcode);
		String userUrl = url.replace("{userName}", userDetails.getUserName()).replace("{hashCode}", hashcode);

		replacements.put("url", userUrl);

		String message = resistrationConformationEmailTemplate.getTemplate(replacements);

		EmailBean email = new EmailBean("WALKIN-SOFTWARE", userDetails.getEmailId(), "User Registration Confiration",
				message);
		email.setHtml(true);
		emailServiceImpl.send(email);
		logger.info("User {} RegistrationCOnfiration mail Sending process End.", userDetails.getUserName());
	}

	public void sendOptMail(UserDetails userDetails) {
		logger.info("User {} RegistrationCOnfiration mail Sending process Start.", userDetails.getUserName());
		if (null == optVerificationEmailTemplate) {
			optVerificationEmailTemplate = new EmailTemplate("sendOtpForVerification.html");
		}

		Map<String, String> replacements = new HashMap<String, String>();
		replacements.put("user", userDetails.getFullName());
		String generateRandomNumber = StringUtil.generateRandomNumber(Constants.INT_SIX);
		replacements.put("code", generateRandomNumber);

		UserOptBean userDto = new UserOptBean(userDetails.getUserName(),userDetails.getEmailId(), generateRandomNumber, new Date());
		CacheData.getOtpGeneratedUserList().add(userDto);

		String message = optVerificationEmailTemplate.getTemplate(replacements);

		EmailBean email = new EmailBean("WALKIN-SOFTWARE", userDetails.getEmailId(), "User OTP Verification", message);
		email.setHtml(true);
		emailServiceImpl.send(email);
		logger.info("User {} forgot password mail Sending process End.", userDetails.getUserName());
	}

	public void sendChangePasswordMail(UserDetails userDetails) {
		logger.info("User {} change password mail Sending process Start.", userDetails.getUserName());
		if (null == changePasswordEmailTemplate) {
			changePasswordEmailTemplate = new EmailTemplate("changePassword.html");
		}

		Map<String, String> replacements = new HashMap<String, String>();
		replacements.put("user", userDetails.getFullName());

		String message = changePasswordEmailTemplate.getTemplate(replacements);

		EmailBean email = new EmailBean("WALKIN-SOFTWARE", userDetails.getEmailId(), "User OTP Verification", message);
		email.setHtml(true);
		emailServiceImpl.send(email);
		logger.info("User {} change password mail Sending process End.", userDetails.getUserName());
	}

}
