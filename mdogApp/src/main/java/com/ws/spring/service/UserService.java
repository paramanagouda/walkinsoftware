package com.ws.spring.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ws.spring.cachedata.CacheData;
import com.ws.spring.dto.UserDto;
import com.ws.spring.dto.UserOptBean;
import com.ws.spring.email.service.AppMailSender;
import com.ws.spring.model.UserDetails;
import com.ws.spring.repository.UserRepository;
import com.ws.spring.util.AESAlgorithm;
import com.ws.spring.util.Constants;
import com.ws.spring.util.DateUtil;

@Component
public class UserService implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	AppMailSender appMailSender;

	public boolean userRegistration(UserDetails userDetails) {
		if (null == userDetails) {
			return Boolean.FALSE;
		}
		userDetails.setRoleId(ROLE_ID_USER);
		userDetails.setIsActive(REGISTERED);
		String encrypt = AESAlgorithm.encrypt(userDetails.getUserName() + "-" + userDetails.getEmailId(),
				userDetails.getUserName());
		userDetails.setHashcode(encrypt);
		UserDetails user1 = userRepository.save(userDetails);
		logger.info("User registration successfull userId : {}", user1.getId());
		appMailSender.sendRegistrationConfirmationMail(userDetails);
		return Boolean.TRUE;
	}

	public boolean verifyHashCode(String userName, String hashCode) {
		UserDetails userDetails = userRepository.findUserDetailsByUserName(userName);
		if (null != userDetails && userDetails.getHashcode().equals(hashCode)) {
			userDetails.setIsActive(ACTIVE);
			userRepository.save(userDetails);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public boolean verifyUserOtp(String emailId, String otp) {
		for (UserOptBean userDto : CacheData.getOtpGeneratedUserList()) {
			if (emailId.equals(userDto.getEmailId()) & otp.equals(userDto.getOpt())) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	public boolean forgotPassword(String emailId) {
		UserDetails userDetailsFromDb = userRepository.findUserDetailsByEmailId(emailId);
		if(null != userDetailsFromDb) {
			appMailSender.sendOptMail(userDetailsFromDb);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	public boolean resetPassword(UserDto userDto) {
		UserDetails userDetailsFromDb = userRepository.findUserDetailsByUserName(userDto.getUserName());
		if(null != userDetailsFromDb) {
			userDetailsFromDb.setPassword(userDto.getNewPassword());
			userDetailsFromDb.setUpdatedDate(DateUtil.convertToLocalDateTimeViaInstant(userDto.getCurrentTime()));
			userRepository.save(userDetailsFromDb);
			appMailSender.sendChangePasswordMail(userDetailsFromDb);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	public boolean changePassword(UserDto userDto) {
		UserDetails userDetailsFromDb = userRepository.findUserDetailsByUserName(userDto.getUserName());
		if(null != userDetailsFromDb) {
			if(userDetailsFromDb.getPassword().equals(userDto.getCurrentPassword()))
			{
				userDetailsFromDb.setPassword(userDto.getNewPassword());
				userDetailsFromDb.setUpdatedDate(DateUtil.convertToLocalDateTimeViaInstant(userDto.getCurrentTime()));
				userRepository.save(userDetailsFromDb);
				appMailSender.sendOptMail(userDetailsFromDb);
			}
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public List<UserDetails> queryAllUserList() {
		return userRepository.findAll();
	}
}
