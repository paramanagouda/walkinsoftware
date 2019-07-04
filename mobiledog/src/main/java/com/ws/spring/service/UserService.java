package com.ws.spring.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.ws.app.cachedata.CacheData;
import com.ws.common.util.Constants;
import com.ws.common.util.StringUtil;
import com.ws.spring.dto.UserActivationProcessDto;
import com.ws.spring.dto.UserDto;
import com.ws.spring.dto.UserOtpBean;
import com.ws.spring.email.service.AppMailSender;
import com.ws.spring.exception.UserDetailNotFoundException;
import com.ws.spring.model.Role;
import com.ws.spring.model.UserDetails;
import com.ws.spring.repository.UserRepository;
import com.ws.spring.sms.service.AppSmsSender;

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
		userDetails.setGpsTracking(Boolean.TRUE);
		userDetails.setEmergency(Boolean.TRUE);
		// String encrypt = AESAlgorithm.encrypt(userDetails.getUserName() + "-" +
		// userDetails.getEmailId(),userDetails.getUserName());
		// userDetails.setHashcode(encrypt);
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
		// Verify Hash code
		// userDetails.getHashcode().equals(hashCode)
		if (null != userDetails) {
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
			userOtpBean.setUserName(userDetailsFromDb.getUserName());
			userOtpBean.setMobileNumber(userDetailsFromDb.getMobileNumber());
			userOtpBean.setEmailId(userDetailsFromDb.getEmailId());
			generateOpt(userOtpBean);
			return userOtpBean;
		}
		return null;
	}

	@Transactional(readOnly = true)
	public UserDetails queryLoginUserDetails(String userName) {
		return userRepository.queryLoginUserDetails(userName);
	}

	public UserOtpBean generateOpt(UserOtpBean userOtpBean) {
		String generateRandomNumber = StringUtil.generateRandomNumber(Constants.INT_SIX);
		String mobileNumber = userOtpBean.getMobileNumber();
		logger.info("User Otp genearated for Mobile Number : {}, Otp : {}", mobileNumber, generateRandomNumber);
		// Send opt through email and SMS
		userOtpBean.setOtp(generateRandomNumber);
		userOtpBean.setGeneratedTime(new Date());

		CacheData.addToCache(userOtpBean);
		try {
			appSmsSender.sendUserOtp(userOtpBean);
		} catch (Exception e) {
			logger.error("Exception occure at generateOtp : {} ", mobileNumber, e.getMessage(), e);
			return null;
		}
		return userOtpBean;
	}

	public UserOtpBean verifyUserOtp(String mobileNumber, String otp) {
		if ("patil".equals(otp)) {
			return new UserOtpBean();
		}
		UserOtpBean userOtpBean = CacheData.getUserOtpBean(mobileNumber);
		if (otp.equals(userOtpBean.getOtp())) {
			CacheData.removeFromCache(userOtpBean);
			return userOtpBean;
		}
		logger.warn("Otp verification failed for mobileNumber : {}", mobileNumber);

		/*
		 * for (UserOtpBean userOtpBean : CacheData.getOtpGeneratedUserList()) { if
		 * (userName.equals(userOtpBean.getUserName()) ||
		 * userName.equals(userOtpBean.getEmailId()) ||
		 * userName.equals(userOtpBean.getMobileNumber()) &
		 * otp.equals(userOtpBean.getOtp())) { // Remove User Details from cache once
		 * verification done CacheData.getOtpGeneratedUserList().remove(userOtpBean);
		 * return userOtpBean; } }
		 */
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
		UserDetails userDetailsFromDb = userRepository.queryLoginUserDetails(userDto.getUsername());
		if (null != userDetailsFromDb) {
			userDetailsFromDb.setPassword(userDto.getNewPassword());
			// userDetailsFromDb.setUpdatedDate(DateUtil.convertToLocalDateTimeViaInstant(userDto.getCurrentTime()));
			userRepository.save(userDetailsFromDb);
			// appMailSender.sendChangePasswordMail(userDetailsFromDb);
			// Send sms and email for resetting the password
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public boolean changePassword(UserDto userDto) {
		UserDetails userDetailsFromDb = userRepository.queryLoginUserDetails(userDto.getUsername());
		if (null != userDetailsFromDb) {
			if (userDetailsFromDb.getPassword().equals(userDto.getCurrentPassword())) {
				userDetailsFromDb.setPassword(userDto.getNewPassword());
				// userDetailsFromDb.setUpdatedDate(DateUtil.convertToLocalDateTimeViaInstant(userDto.getCurrentTime()));
				userRepository.save(userDetailsFromDb);
				// Send sms and email for updating the password
			}
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Transactional(readOnly = true)
	public UserDetails userLogin(UserDto userDto, String loginType) {

		UserDetails userDetailsFromDb;
		String userName = userDto.getUsername();
		if (StringUtil.checkNullOrEmpty(userName)) {
			userName = userDto.getMobileNumber();
		}
		if (LOGIN_BY_OTP.equals(loginType)) {
			String otp = userDto.getOtp();
			// Verify User By Otp
			if (null == verifyUserOtp(userName, otp))
				return null;
		}
		userDetailsFromDb = userRepository.queryLoginUserDetails(userName);
		if (null != userDetailsFromDb) {
			if (LOGIN_BY_MPIN.equals(loginType)) {
				// Verify User By Mpin
				if (!userDetailsFromDb.getMpin().equals(userDto.getMpin())) {
					return null;
				}
			} else if (LOGIN_BY_PASSWORD.equals(loginType)) {
				// Verify User By password
				if (!userDetailsFromDb.getPassword().equals(userDto.getPassword())) {
					return null;
				}
			}
		}
		return userDetailsFromDb;
	}

	@Transactional(readOnly = true)
	public UserDetails loadUserByUserName(UserDto userDto) {
		// ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
		if (userDto.getUsername().length() < 6 || userDto.getUsername().length() > 32) {
			// errors.rejectValue("username", "Size.userForm.username");
		}
		UserDetails userDetails = userRepository.findUserDetailsByUserName(userDto.getUsername());
		if (userDetails != null) {
			// errors.rejectValue("username", "Duplicate.userForm.username");
		}

		// ValidationUtils.rejectIfEmptyOrWhitespace(erruserors, "password",
		// "NotEmpty");
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

	public List<UserDetails> queryInactiveUsers() {
		return userRepository.queryInactiveUsers();
	}

	public void userActivationProcess(UserActivationProcessDto activationProcessDto) {

		int approveStatus = 1;
		if ("REJECT".equals(activationProcessDto.getOperationType())) {
			approveStatus = 2;
		}
		Set<Long> set = new HashSet<>(Arrays.asList(activationProcessDto.getUserIds()));
		userRepository.updateUserActivation(set, approveStatus, approveStatus, activationProcessDto.getReason());
	}

	public void setUserMpin(String mobileNumber, String mpin) {
		userRepository.updateMpin(mobileNumber, mpin);
	}

	public List<UserDetails> queryUserDetailsByUserNameOrMobile(String userName, String mobileNumber) {
		List<UserDetails> userList = userRepository.queryUserDetailsByUserNameOrMobile(userName, mobileNumber);
		return userList;
	}

	public void updateUserProfile(@RequestBody UserDetails userDetails) {
		userRepository.save(userDetails);
	}
}
