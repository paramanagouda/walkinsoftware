package com.ws.spring.email.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ws.spring.dto.EmailBean;
import com.ws.spring.dto.UserOtpBean;
import com.ws.spring.model.UserDetails;

@Component
public class AppMailSender {

	Logger logger = LogManager.getLogger(this.getClass().getName());

	@Autowired
	EmailServiceImpl emailServiceImpl;

	private EmailTemplate resistrationConformationEmailTemplate;
	private EmailTemplate optVerificationEmailTemplate;
	private EmailTemplate changePasswordEmailTemplate;

	private String url = "http://localhost:8080/home/v1/verifyHashCode?userName={userName}&hashCode={hashCode}";

	public void sendRegistrationConfirmationMail(UserDetails userDetails) {
		try {
			logger.info("User {} RegistrationCOnfiration mail Sending process Start.", userDetails.getUserName());
			if (null == resistrationConformationEmailTemplate) {
				resistrationConformationEmailTemplate = new EmailTemplate("registrationConfirmation.html");
			}

			Map<String, String> replacements = new HashMap<String, String>();
			replacements.put("user", userDetails.getFullName());
			replacements.put("today", String.valueOf(userDetails.getInsertedDate()));
			// logger.info("HAshCode Generated : {}", userDetails.getHashcode());
			// String hashcode = StringUtil.encode(userDetails.getHashcode());
			// logger.info("HashCode after encoding : {}", hashcode);
			String userUrl = url.replace("{userName}", userDetails.getUserName());

			replacements.put("url", userUrl);

			String message = resistrationConformationEmailTemplate.getTemplate(replacements);

			EmailBean email = new EmailBean("WALKIN-SOFTWARE", userDetails.getEmailId(),
					"User Registration Confiration", message);
			email.setHtml(true);
			emailServiceImpl.send(email);
			logger.info("User {} RegistrationCOnfiration mail Sending process End.", userDetails.getUserName());
		} catch (Exception e) {
			logger.error("Excpetion occured while sending registtration mail to user : {}", e.getMessage(), e);
		}
	}

	public void sendOptMail(UserOtpBean userOptBean) {
		try {
			logger.info("User {} mail Sending process Start.", userOptBean.getUserName());
			if (null == optVerificationEmailTemplate) {
				optVerificationEmailTemplate = new EmailTemplate("sendOtpForVerification.html");
			}

			Map<String, String> replacements = new HashMap<String, String>();
			replacements.put("user", userOptBean.getUserName());
			replacements.put("code", userOptBean.getOtp());

			/*
			 * UserOptBean userDto = new UserOptBean(userDetails.getUserName(),
			 * userDetails.getEmailId(), userDetails.getMobileNumber(),
			 * generateRandomNumber, new Date());
			 * CacheData.getOtpGeneratedUserList().add(userDto);
			 */

			String message = optVerificationEmailTemplate.getTemplate(replacements);

			EmailBean email = new EmailBean("WALKIN-SOFTWARE", userOptBean.getEmailId(), userOptBean.getMobileNumber(),
					"User OTP Verification", message);
			email.setHtml(true);
			emailServiceImpl.send(email);
			logger.info("User {} forgot password mail Sending process End.", userOptBean.getUserName());
		} catch (Exception e) {
			logger.error("Excpetion occured while sending registtration mail to user : {}", e.getMessage(), e);
		}
	}

	public void sendChangePasswordMail(UserDetails userDetails) {
		try {
			logger.info("User {} change password mail Sending process Start.", userDetails.getUserName());
			if (null == changePasswordEmailTemplate) {
				changePasswordEmailTemplate = new EmailTemplate("changePassword.html");
			}

			Map<String, String> replacements = new HashMap<String, String>();
			replacements.put("user", userDetails.getFullName());

			String message = changePasswordEmailTemplate.getTemplate(replacements);

			EmailBean email = new EmailBean("WALKIN-SOFTWARE", userDetails.getEmailId(), "User OTP Verification",
					message);
			email.setHtml(true);
			emailServiceImpl.send(email);
			logger.info("User {} change password mail Sending process End.", userDetails.getUserName());
		} catch (Exception e) {
			logger.error("Excpetion occured while sending registtration mail to user : {}", e.getMessage(), e);
		}
	}

}
