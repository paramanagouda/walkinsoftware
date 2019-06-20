package com.ws.spring.rest.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

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

import com.ws.common.util.ClientResponseUtil;
import com.ws.common.util.Constants;
import com.ws.common.util.StringUtil;
import com.ws.spring.dto.UserActivationProcessDto;
import com.ws.spring.dto.UserDto;
import com.ws.spring.dto.UserOtpBean;
import com.ws.spring.exception.ClientResponseBean;
import com.ws.spring.model.UserDetails;
import com.ws.spring.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/home")
@Api(value = "User Management System", description = "Operations pertaining to user in User Management System")
public class HomeRestController {

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
	@ApiOperation(value = "User Registration", response = ClientResponseBean.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "User Registration successful"),
			@ApiResponse(code = 1001, message = "User Opt validation failed."),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping("/v1/userRegistration")
	@ResponseStatus(HttpStatus.CREATED)
	public ClientResponseBean userRegistration(
			@ApiParam(value = "UserDetail store in database table", required = true) @Valid @RequestBody UserDetails user) {
		logger.debug("userRegistration for UserName : {}", user.getUserName());
		try {
			if (null == userService.verifyUserOtp(user.getMobileNumber(), user.getOtp())) {
				return ClientResponseUtil.getUserOptValidationFailed();
			}
			UserDetails userRegistration = userService.userRegistration(user);
			if (null != userRegistration) {
				return ClientResponseUtil.userRegistrationSuccess();
			}
			return ClientResponseUtil.UserRegistrationFailed();
		} catch (Exception ex) {
			logger.error("Exception Occure : {} ", ex.getMessage(), ex);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User Registration got an Error", ex);
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
		logger.debug("verifyHashCode for UserName : {}", userName);
		try {
			// hashCode = StringUtil.decode(hashCode);
			UserDetails userDetails = userService.verifyHashCode(userName, hashCode);
			if (null != userDetails) {
				return ClientResponseUtil.getUserOptValidationSuccess();
			}
			return ClientResponseUtil.getUserOptValidationFailed();
		} catch (Exception ex) {
			logger.error("Exception Occure : {} ", ex.getMessage(), ex);
			throw new ResponseStatusException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, "Exception Occured", ex);
		}
	}

	@ApiOperation(value = "Generate Otp for validation", response = ClientResponseBean.class)
	@PostMapping("/v1/generateOtp")
	public ClientResponseBean generateOtp(@RequestBody UserOtpBean userOptBean) {
		logger.debug("generate Otp for MobileNumber : {}", userOptBean.getMobileNumber());
		try {
			UserOtpBean userOptBeanReturn = null;
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
			logger.error("Exception Occure : {} ", ex.getMessage(), ex);
			throw new ResponseStatusException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, "Exception Occured", ex);
		}
	}

	@GetMapping("/v1/verifyUserOtp")
	public ClientResponseBean verifyUserOtp(@RequestBody UserDto userDto) {
		logger.debug("verifyUserOtp Otp for userName : {} and opt : {}", userDto.getUserName(), userDto.getOtp());
		try {
			UserOtpBean userOptBean = userService.verifyUserOtp(userDto.getUserName(), userDto.getOtp());
			if (null != userOptBean) {
				return ClientResponseUtil.getUserOptValidationSuccess();
			}
			return ClientResponseUtil.getUserOptValidationFailed();
		} catch (Exception ex) {
			logger.error("Exception Occure : {} ", ex.getMessage(), ex);
			throw new ResponseStatusException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, "Exception Occured", ex);
		}
	}

	@GetMapping("/v1/setUserMpin")
	public ClientResponseBean setUserMpin(@RequestParam(name = "mobileNumber") String mobileNumber,
			@RequestParam(name = "mpin") String mpin) {
		logger.debug("setUserMpin for mobileNumber : {}", mobileNumber);
		try {
			userService.setUserMpin(mobileNumber, mpin);
			return ClientResponseUtil.getSuccessResponse();
		} catch (Exception ex) {
			logger.error("Exception Occure : {} ", ex.getMessage(), ex);
			throw new ResponseStatusException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, "Exception Occured", ex);
		}
	}

	@GetMapping("/v1/forgotPassword")
	public ClientResponseBean forgotPassword(@RequestParam(name = "mobileNumber") String mobileNumber) {
		logger.debug("forgotPassword for mobileNumber : {}", mobileNumber);
		try {
			if (userService.forgotPassword(mobileNumber)) {
				return ClientResponseUtil.sentOptSucces();
			}
			logger.warn("forgotPassword : User Details not found for mobileNUmber : {}", mobileNumber);
			return ClientResponseUtil.sentOptFailed();
		} catch (Exception ex) {
			logger.error("Exception Occure : {} ", ex.getMessage(), ex);
			throw new ResponseStatusException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, "Exception Occured", ex);
		}
	}

	@PostMapping("/v1/resetPassword")
	public ClientResponseBean resetPassword(@RequestBody UserDto userDto) {
		logger.debug("resetPassword for user : {}", userDto.getUserName());
		// verify Otp
		try {
			if (userService.resetPassword(userDto)) {
				return ClientResponseUtil.getSuccessResponse();
			}
			return ClientResponseUtil.getErrorResponse();
		} catch (Exception ex) {
			logger.error("Exception Occure : {} ", ex.getMessage(), ex);
			throw new ResponseStatusException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, "Exception Occured", ex);
		}
	}

	@PostMapping("/v1/changePassword")
	public ClientResponseBean changePassword(@RequestBody UserDto userDto) {
		logger.debug("changePassword for user : {}", userDto.getUserName());
		try {
			if (userService.changePassword(userDto)) {
				return ClientResponseUtil.getSuccessResponse();
			}
			return ClientResponseUtil.getErrorResponse();
		} catch (Exception ex) {
			logger.error("Exception Occure : {} ", ex.getMessage(), ex);
			throw new ResponseStatusException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, "Exception Occured", ex);
		}
	}

	// Login with fingerprint option need to implement
	@PostMapping("/v1/userLoginByPassword")
	public ClientResponseBean userLoginByPassword(@RequestBody UserDto userDto) {
		logger.debug("userLoginByPassword for user : {}", userDto.getUserName());
		try {
			logger.info("User login process : {}", userDto);
			UserDetails userDetails = userService.userLogin(userDto, Constants.LOGIN_BY_PASSWORD);
			if (null != userDetails) {
				logger.info("User login process success : {}", userDto.getUserName());
				return ClientResponseUtil.userLoginSuccess();
			}
			logger.info("User login process failed : {}", userDto.getUserName());
			return ClientResponseUtil.userLoginFailed();
		} catch (Exception ex) {
			logger.error("Exception Occure : {} ", ex.getMessage(), ex);
			throw new ResponseStatusException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, "Exception Occured", ex);
		}
	}

	@PostMapping("/v1/userLoginByOtp")
	public ClientResponseBean userLoginByOtp(@RequestBody UserDto userDto) {
		logger.debug("userLoginByOtp for user : {}", userDto.getUserName());
		try {
			logger.info("User login process : {}", userDto);
			UserDetails userDetails = userService.userLogin(userDto, Constants.LOGIN_BY_OTP);
			if (null != userDetails) {
				logger.info("User login process success : {}", userDto.getUserName());
				return ClientResponseUtil.userLoginSuccess();
			}
			logger.info("User login process failed : {}", userDto.getUserName());
			return ClientResponseUtil.userLoginFailed();
		} catch (Exception ex) {
			logger.error("Exception Occure : {} ", ex.getMessage(), ex);
			throw new ResponseStatusException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, "Exception Occured", ex);
		}
	}

	@PostMapping("/v1/userLoginMpin")
	public ClientResponseBean userLoginMpin(@RequestBody UserDto userDto) {
		logger.debug("userLoginMpin for user : {}", userDto.getUserName());
		try {
			logger.info("User login process : {}", userDto);
			UserDetails userDetails = userService.userLogin(userDto, Constants.LOGIN_BY_MPIN);
			if (null != userDetails) {
				logger.info("User login process success : {}", userDto.getUserName());
				return ClientResponseUtil.userLoginSuccess();
			}
			logger.warn("User login process failed : {}", userDto.getUserName());
			return ClientResponseUtil.userLoginFailed();
		} catch (Exception ex) {
			logger.error("Exception Occure : {} ", ex.getMessage(), ex);
			throw new ResponseStatusException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, "Exception Occured", ex);
		}
	}

	@GetMapping("/v1/queryInactiveUsers")
	public List<UserDetails> queryInactiveUsers() {
		logger.debug("queryInactiveUsers ");
		try {
			return userService.queryInactiveUsers();
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, "Exception Occured", ex);
		}
	}

	@PostMapping("/v1/userActivationProcess")
	public ClientResponseBean userActivationProcess(@RequestBody UserActivationProcessDto activationProcessDto) {
		logger.debug("userActivationProcess ");
		try {
			userService.userActivationProcess(activationProcessDto);
			return ClientResponseUtil.getSuccessResponse();
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, "Exception Occured ", ex);
		}
	}
}
