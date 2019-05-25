package com.ws.spring.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ws.spring.cachedata.CacheData;
import com.ws.spring.dto.UserDto;
import com.ws.spring.dto.UserOptBean;
import com.ws.spring.email.service.AppMailSender;
import com.ws.spring.model.Role;
import com.ws.spring.model.UserDetails;
import com.ws.spring.repository.UserRepository;
import com.ws.spring.util.AESAlgorithm;
import com.ws.spring.util.Constants;
import com.ws.spring.util.DateUtil;

@Component
public class UserService implements Constants {

	Logger logger = LogManager.getLogger(this.getClass().getName());

	@Autowired
	UserRepository userRepository;
	
	

	@Autowired
	AppMailSender appMailSender;

	public UserDetails userRegistration(UserDetails userDetails) {
		if (null == userDetails) {
			return null;
		}
		Role role = new Role(4L,"User");
		userDetails.setRole(role);
		userDetails.setIsActive(REGISTERED);
		String encrypt = AESAlgorithm.encrypt(userDetails.getUserName() + "-" + userDetails.getEmailId(),
				userDetails.getUserName());
		userDetails.setHashcode(encrypt);
		UserDetails user1 = userRepository.save(userDetails);
		logger.info("User registration successfull userId : {}", user1.getId());
		appMailSender.sendRegistrationConfirmationMail(userDetails);
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

	public UserOptBean verifyUserOtp(String emailId, String otp) {
		for (UserOptBean userOptBean : CacheData.getOtpGeneratedUserList()) {
			if (emailId.equals(userOptBean.getEmailId()) & otp.equals(userOptBean.getOpt())) {
				return userOptBean;
			}
		}
		return null;
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
		UserDetails userDetailsFromDb = userRepository.findUserDetailsByUserName(userDto.getUsername());
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
		UserDetails userDetailsFromDb = userRepository.findUserDetailsByUserName(userDto.getUsername());
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
	
	public UserDetails userLogin(UserDto userDto) {
		UserDetails userDetailsFromDb = userRepository.findUserDetailsByUserName(userDto.getUsername());
		if(null != userDetailsFromDb) {
			if(userDetailsFromDb.getPassword().equals(userDto.getPassword()))
			{
				return userDetailsFromDb;
			}
		}
		return null;
	}

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(UserDto  userDto) {
    	//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
		if (userDto.getUsername().length() < 6 || userDto.getUsername().length() > 32) {
			//errors.rejectValue("username", "Size.userForm.username");
		}
		UserDetails userDetails = userRepository.findUserDetailsByUserName(userDto.getUsername());
		if (userDetails != null) {
			//errors.rejectValue("username", "Duplicate.userForm.username");
		}

		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
		if (userDto.getPassword().length() < 8 || userDto.getPassword().length() > 32) {
			//errors.rejectValue("password", "Size.userForm.password");
		}

		if (!userDto.getPassword().equals(userDetails.getPassword())) {
			//errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
		}
		return userDetails;
    }
	
	public List<UserDetails> queryAllUserList() {
		return userRepository.findAll();
	}
}
