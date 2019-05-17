package com.ws.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ws.spring.dto.UserDto;
import com.ws.spring.exception.ClientResponseBean;
import com.ws.spring.model.UserDetails;
import com.ws.spring.service.UserService;
import com.ws.spring.util.ClientResponseUtil;

@RestController
@RequestMapping("/home/v1/")
public class HomeController {

	@Autowired
	UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * userRegistration this method will register user into system
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping("/userRegistration/")
	@ResponseStatus(org.springframework.http.HttpStatus.CREATED)
	public ClientResponseBean userRegistration(@RequestBody UserDetails user) {
		if (userService.userRegistration(user)) {
			return ClientResponseUtil.getSuccessResponse();
		}
		return ClientResponseUtil.getErrorResponse();
	}

	/**
	 * Verify hash code and is it matches then based on the is_active field send
	 * response to user if IS_Active is 0 then send user registration is completed
	 * if IS_ACTIVE is 1 then send reset password page to user
	 * 
	 * @return
	 */
	@GetMapping(value = "/verifyHashCode")
	public ClientResponseBean verifyHashCode(@RequestParam("userName") String userName,
			@RequestParam("hashCode") String hashCode) {
		// hashCode = StringUtil.decode(hashCode);
		if (userService.verifyHashCode(userName, hashCode)) {
			return ClientResponseUtil.getSuccessResponse();
		}
		return ClientResponseUtil.getErrorResponse();
	}

	@GetMapping("/verifyUserOtp")
	public ClientResponseBean verifyUserOtp(@RequestParam(name = "emailId") String emailId,
			@RequestParam(name = "opt") String otp) {
		if (userService.verifyHashCode(emailId, otp)) {
			return ClientResponseUtil.getSuccessResponse();
		}
		return ClientResponseUtil.getErrorResponse();
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
}
