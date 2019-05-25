package com.ws.spring.controller;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ws.spring.dto.UserDto;
import com.ws.spring.dto.UserOptBean;
import com.ws.spring.exception.ClientResponseBean;
import com.ws.spring.model.UserDetails;
import com.ws.spring.service.UserService;
import com.ws.spring.util.ClientResponseUtil;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/home/v1")
public class HomeController {

	@Autowired
	UserService userService;
	
	Logger logger = LogManager.getLogger(this.getClass().getName());
	
	@GetMapping("/index")
	public String index()
	{
		logger.info("Loading index page");
		return "index";
	}


	@GetMapping("/testing")
	public String testing()
	{
		logger.info("App testing {}", new Date());
		return "App is up and running";
	}
	
	/**
	 * userRegistration this method will register user into system
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping("/userRegistration")
	@ResponseStatus(HttpStatus.CREATED)
	public ClientResponseBean<UserDetails> userRegistration(@RequestBody UserDetails user) {

		UserDetails userRegistration = userService.userRegistration(user);
		if (null != userRegistration) {
			return new ClientResponseBean<>(HttpStatus.CREATED.value(), "User Registration successfully.",
					"User Registration successfully.", userRegistration);
		}
		return new ClientResponseBean<>(HttpStatus.BAD_REQUEST.value(), "User Registration failed.",
				"User Registration failed.", user);
	}

	/**
	 * Verify hash code and is it matches then based on the is_active field send
	 * response to user if IS_Active is 0 then send user registration is completed
	 * if IS_ACTIVE is 1 then send reset password page to user
	 * 
	 * @return
	 */
	@GetMapping(value = "/verifyHashCode")
	public ClientResponseBean<UserDetails> verifyHashCode(@RequestParam("userName") String userName,
			@RequestParam("hashCode") String hashCode) {
		// hashCode = StringUtil.decode(hashCode);
		UserDetails userDetails = userService.verifyHashCode(userName, hashCode);
		if (null != userDetails) {
			return new ClientResponseBean<>(HttpStatus.OK.value(), "User Verification Completed.",
					"User Verification Completed.", userDetails);
		}
		return new ClientResponseBean<>(HttpStatus.BAD_REQUEST.value(), "User Verification Failed.",
				"User Verification failed.", userName);
	}

	@GetMapping("/verifyUserOtp")
	public ClientResponseBean<UserOptBean> verifyUserOtp(@RequestParam(name = "emailId") String emailId,
			@RequestParam(name = "opt") String otp) {
		// hashCode = StringUtil.decode(hashCode);
		UserOptBean userOptBean = userService.verifyUserOtp(emailId, otp);
		if (null != userOptBean) {
			return new ClientResponseBean<>(HttpStatus.OK.value(), "User Opt Verification Completed.",
					"User Opt Verification Completed.", userOptBean);
		}
		return new ClientResponseBean<>(HttpStatus.BAD_REQUEST.value(), "User Opt Verification Failed.",
				"User Opt Verification failed.", emailId);
	}

	@GetMapping("/forgotPassword")
	public ClientResponseBean forgotPassword(@RequestParam(name = "emailId") String emailId) {
		if (userService.forgotPassword(emailId)) {
			return ClientResponseUtil.getSuccessResponse();
		}
		return ClientResponseUtil.getErrorResponse();
	}

	@PostMapping("/resetPassword")
	public ClientResponseBean resetPassword(@RequestBody UserDto userDto) {
		if (userService.resetPassword(userDto)) {
			return ClientResponseUtil.getSuccessResponse();
		}
		return ClientResponseUtil.getErrorResponse();
	}

	@PostMapping("/changePassword")
	public ClientResponseBean changePassword(@RequestBody UserDto userDto) {
		if (userService.changePassword(userDto)) {
			return ClientResponseUtil.getSuccessResponse();
		}
		return ClientResponseUtil.getErrorResponse();
	}

	@PostMapping("/userLogin")
	public ClientResponseBean userLogin(@RequestBody UserDto userDto) {
		logger.info("User login process : {}", userDto.getUsername());
		UserDetails userDetails = userService.userLogin(userDto);
		if (null != userDetails) {
			logger.info("User login process success : {}", userDto.getUsername());
			return new ClientResponseBean<UserDetails>(HttpStatus.OK.value(), "User Login Success.",
					"User Login Success.", userDetails);
		}
		logger.info("User login process failed : {}", userDto.getUsername());
		return new ClientResponseBean<>(HttpStatus.BAD_REQUEST.value(), "User Login failed.",
				"User name or Password not matching.", userDetails);
	}
}
