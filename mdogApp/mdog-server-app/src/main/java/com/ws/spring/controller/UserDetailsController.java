package com.ws.spring.controller;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ws.spring.dto.UserDto;
import com.ws.spring.dto.UserOptBean;
import com.ws.spring.exception.ClientResponseBean;
import com.ws.spring.model.UserDetails;
import com.ws.spring.service.UserService;
import com.ws.spring.util.ClientResponseUtil;
import com.ws.spring.util.Constants;
import com.ws.spring.util.StringUtil;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserDetailsController {

	@Autowired
	UserService userService;

	Logger logger = LogManager.getLogger(this.getClass().getName());

	@GetMapping("/v1/index")
	public String index() {
		logger.info("Loading index page");
		return "index";
	}

	@GetMapping("/v1/testing")
	public String testing() {
		logger.info("App testing {}", new Date());
		return "App is up and running";
	}

	/**
	 * userRegistration this method will register user into system
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping("/v1/userRegistration")
	@ResponseStatus(HttpStatus.CREATED)
	public ClientResponseBean userRegistration(@RequestBody UserDetails user) {
		try {

			UserDetails userRegistration = userService.userRegistration(user);
			if (null != userRegistration) {
				return ClientResponseUtil.userRegistrationSuccess();
			}
			return ClientResponseUtil.UserRegistrationFailed();
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Registration got an Error", ex);
		}
	}

	/**
	 * Verify hash code and is it matches then based on the is_active field send
	 * response to user if IS_Active is 0 then send user registration is completed
	 * if IS_ACTIVE is 1 then send reset password page to user
	 * 
	 * @return
	 */
	@GetMapping(value = "/v1/verifyHashCode")
	public ClientResponseBean verifyHashCode(@RequestParam("userName") String userName,
			@RequestParam("hashCode") String hashCode) {
		try {
			// hashCode = StringUtil.decode(hashCode);
			UserDetails userDetails = userService.verifyHashCode(userName, hashCode);
			if (null != userDetails) {
				return ClientResponseUtil.getUserOptValidationSuccess();
			}
			return ClientResponseUtil.getUserOptValidationFailed();
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, "Exception Occured", ex);
		}
	}

	@PostMapping("/v1/generateOtp")
	public ClientResponseBean generateOtp(@RequestBody UserOptBean userOptBean) {
		try {
			UserOptBean userOptBeanReturn = null;
			if (!StringUtil.checkNullOrEmpty(userOptBean.getActivity())
					&& Constants.REGISTRATION_STR.equals(userOptBean.getActivity())) {
				userOptBeanReturn = userService.generateOpt(userOptBean);
			} else {

				userOptBeanReturn = userService.sendOtp(userOptBean);
			}
			if (null != userOptBeanReturn) {
				return ClientResponseUtil.sentOptSucces();
			}
			return ClientResponseUtil.sentOptFailed();
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, "Exception Occured", ex);
		}
	}

	@GetMapping("/v1/verifyUserOtp")
	public ClientResponseBean verifyUserOtp(@RequestParam(name = "userName") String userName,
			@RequestParam(name = "opt") String otp) {
		try {
			// hashCode = StringUtil.decode(hashCode);
			UserOptBean userOptBean = userService.verifyUserOtp(userName, otp);
			if (null != userOptBean) {
				return ClientResponseUtil.getUserOptValidationSuccess();
			}
			return ClientResponseUtil.getUserOptValidationFailed();
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, "Exception Occured", ex);
		}
	}

	@GetMapping("/v1/forgotPassword")
	public ClientResponseBean forgotPassword(@RequestParam(name = "emailId") String emailId) {
		try {
			if (userService.forgotPassword(emailId)) {
				return ClientResponseUtil.sentOptSucces();
			}
			return ClientResponseUtil.sentOptFailed();
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, "Exception Occured", ex);
		}
	}

	@PostMapping("/v1/resetPassword")
	public ClientResponseBean resetPassword(@RequestBody UserDto userDto) {
		try {
			if (userService.resetPassword(userDto)) {
				return ClientResponseUtil.getSuccessResponse();
			}
			return ClientResponseUtil.getErrorResponse();
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, "Exception Occured", ex);
		}
	}

	@PostMapping("/v1/changePassword")
	public ClientResponseBean changePassword(@RequestBody UserDto userDto) {
		try {
			if (userService.changePassword(userDto)) {
				return ClientResponseUtil.getSuccessResponse();
			}
			return ClientResponseUtil.getErrorResponse();
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, "Exception Occured", ex);
		}
	}

	@PostMapping("/v1/userLogin")
	public ClientResponseBean userLogin(@RequestBody UserDto userDto) {
		try {
			logger.info("User login process : {}", userDto);
			UserDetails userDetails = userService.userLogin(userDto);
			if (null != userDetails) {
				logger.info("User login process success : {}", userDto.getUsername());
				return ClientResponseUtil.userLoginSuccess();
			}
			logger.info("User login process failed : {}", userDto.getUsername());
			return ClientResponseUtil.userLoginFailed();
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, "Exception Occured", ex);
		}
	}
}
