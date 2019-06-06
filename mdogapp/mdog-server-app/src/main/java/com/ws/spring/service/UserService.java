package com.ws.spring.service;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ws.spring.cachedata.CacheData;
import com.ws.spring.dto.UserDto;
import com.ws.spring.dto.UserOtpBean;
import com.ws.spring.email.service.AppMailSender;
import com.ws.spring.exception.UserDetailNotFoundException;
import com.ws.spring.model.Role;
import com.ws.spring.model.UserDetails;
import com.ws.spring.repository.UserRepository;
import com.ws.spring.sms.service.AppSmsSender;
import com.ws.spring.util.AESAlgorithm;
import com.ws.spring.util.Constants;
import com.ws.spring.util.DateUtil;
import com.ws.spring.util.StringUtil;

@Component
public class UserService implements Constants {

	Logger logger = LogManager.getLogger(this.getClass().getName());

	@Autowired
	UserRepository userRepository;

	@Autowired
	AppMailSender appMailSender;

	@Autowired
	AppSmsSender appSmsSender;

	public UserDetails userRegistration(UserDetails userDetails) {
		if (null == userDetails) {
			return null;
		}
		Role role = new Role(4L, "User");
		userDetails.setRole(role);
		userDetails.setIsActive(REGISTERED);
		String encrypt = AESAlgorithm.encrypt(userDetails.getUserName() + "-" + userDetails.getEmailId(),
				userDetails.getUserName());
		userDetails.setHashcode(encrypt);
		UserDetails user1 = userRepository.save(userDetails);
		logger.info("User registration successsfull userId : {}", user1.getId());
		if (!StringUtil.checkNullOrEmpty(userDetails.getEmailId())) {
			appMailSender.sendRegistrationConfirmationMail(userDetails);
		}
		// Send sms to user
		// Send mail to admin
		return user1;
	}

	public UserDetails verifyHashCode(String userName, String hashCode) {
		UserDetails userDetails = userRepository.findUserDetailsByUserName(userName);
		if (null != userDetails && userDetails.getHashcode().equals(hashCode)) {
			userDetails.setIsActive(ACTIVE);
			userRepository.save(userDetails);
			return userDetails;
		}
		return null;
	}

	@Transactional(readOnly = true)
	public UserOtpBean sendOtp(UserOtpBean userOtpBean) {

		String userName = userOtpBean.getUserName();
		if (StringUtil.checkNullOrEmpty(userName)) {
			userName = userOtpBean.getEmailId();
		}
		if (StringUtil.checkNullOrEmpty(userName)) {
			userName = userOtpBean.getMobileNumber();
		}
		UserDetails userDetailsFromDb = userRepository.queryLoginUserDetails(userName);
		if (null != userDetailsFromDb) {
			generateOpt(userOtpBean);
			return userOtpBean;
		}
		return null;
	}

	public UserOtpBean generateOpt(UserOtpBean userOtpBean) {
		String generateRandomNumber = StringUtil.generateRandomNumber(Constants.INT_SIX);
		logger.info("User Otp genearated for Mobile Number : {}, Otp : {}", userOtpBean.getMobileNumber(),
				generateRandomNumber);
		// Send opt through email and SMS
		userOtpBean.setOtp(generateRandomNumber);
		userOtpBean.setGeneratedTime(new Date());
		CacheData.getOtpGeneratedUserList().add(userOtpBean);
		return userOtpBean;
	}

	public UserOtpBean verifyUserOtp(String userName, String otp) {
		for (UserOtpBean userOtpBean : CacheData.getOtpGeneratedUserList()) {
			if (userName.equals(userOtpBean.getUserName()) || userName.equals(userOtpBean.getEmailId())
					|| userName.equals(userOtpBean.getMobileNumber()) & otp.equals(userOtpBean.getOtp())) {
				return userOtpBean;
			}
		}
		return null;
	}

	@Transactional(readOnly = true)
	public boolean forgotPassword(String mobile) throws UserDetailNotFoundException {
		UserDetails userDetailsFromDb = userRepository.queryLoginUserDetails(mobile);
		if (null != userDetailsFromDb) {
			UserOtpBean userOtpBean = new UserOtpBean();
			userOtpBean.setMobileNumber(mobile);
			userOtpBean = generateOpt(userOtpBean);
			// appMailSender.sendOptMail(userDetailsFromDb);
			// appSmsSender.sendUserOtp(userOtpBean);
			return Boolean.TRUE;
		}
		throw new UserDetailNotFoundException("User Details not found");
	}

	public boolean resetPassword(UserDto userDto) {
		UserDetails userDetailsFromDb = userRepository.findUserDetailsByUserName(userDto.getUsername());
		if (null != userDetailsFromDb) {
			userDetailsFromDb.setPassword(userDto.getNewPassword());
			userDetailsFromDb.setUpdatedDate(DateUtil.convertToLocalDateTimeViaInstant(userDto.getCurrentTime()));
			userRepository.save(userDetailsFromDb);
			appMailSender.sendChangePasswordMail(userDetailsFromDb);
			// Send sms and email for resetting the password
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public boolean changePassword(UserDto userDto) {
		UserDetails userDetailsFromDb = userRepository.findUserDetailsByUserName(userDto.getUsername());
		if (null != userDetailsFromDb) {
			if (userDetailsFromDb.getPassword().equals(userDto.getCurrentPassword())) {
				userDetailsFromDb.setPassword(userDto.getNewPassword());
				userDetailsFromDb.setUpdatedDate(DateUtil.convertToLocalDateTimeViaInstant(userDto.getCurrentTime()));
				userRepository.save(userDetailsFromDb);
				// Send sms and email for updating the password
			}
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Transactional(readOnly = true)
	public UserDetails userLogin(UserDto userDto) {
		UserDetails userDetailsFromDb = userRepository.findUserDetailsByUserName(userDto.getUsername());
		if (null != userDetailsFromDb) {
			if (userDetailsFromDb.getPassword().equals(userDto.getPassword())) {
				return userDetailsFromDb;
			}
		}
		return null;
	}

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(UserDto userDto) {
		// ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
		if (userDto.getUsername().length() < 6 || userDto.getUsername().length() > 32) {
			// errors.rejectValue("username", "Size.userForm.username");
		}
		UserDetails userDetails = userRepository.findUserDetailsByUserName(userDto.getUsername());
		if (userDetails != null) {
			// errors.rejectValue("username", "Duplicate.userForm.username");
		}

		// ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
		if (userDto.getPassword().length() < 8 || userDto.getPassword().length() > 32) {
			// errors.rejectValue("password", "Size.userForm.password");
		}

		if (!userDto.getPassword().equals(userDetails.getPassword())) {
			// errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
		}
		return userDetails;
	}

	public List<UserDetails> queryAllUserList() {
		return userRepository.findAll();
	}
}
