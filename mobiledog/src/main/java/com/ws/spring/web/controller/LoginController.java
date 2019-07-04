package com.ws.spring.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ws.common.util.Constants;
import com.ws.common.util.StringUtil;
import com.ws.spring.dto.UserDto;
import com.ws.spring.model.UserDetails;
import com.ws.spring.service.UserService;

@Controller
public class LoginController {

	Logger logger = LogManager.getLogger(this.getClass().getName());

	@Autowired
	UserService userService;

	@GetMapping("/")
	public String index(Model model, HttpSession session) {
		model.addAttribute("date", new Date());
		model.addAttribute("sessionId", session.getId());
		return "index";
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/loginByPassword")
	public String userLoginByPassword(Model model, @RequestParam String userName, @RequestParam String password,
			HttpServletRequest request) {
		List<String> userSession = null;
		try {
			userSession = (List<String>) request.getSession().getAttribute("USER_SESSION");
		} catch (Exception e) {
			logger.warn("Exception occured while getting session Attributes");
		}
		if (userSession == null) {
			userSession = new ArrayList<>();
			request.getSession().setAttribute("USER_SESSION", userName);
		}
		userSession.add(userName);
		UserDto userDto = new UserDto();
		userDto.setUsername(userName);
		userDto.setPassword(password);
		UserDetails userDetails = userService.userLogin(userDto, Constants.LOGIN_BY_PASSWORD);
		if (null == userDetails) {
			model.addAttribute("errorMessage", "Invalid Credentials");
			return "login";
		}
		model.addAttribute("userdetails", userDetails);

		return "welcome";
	}

	@RequestMapping(value = "/queryUserDetailsList", method = RequestMethod.GET)
	public ModelAndView queryUserDetailsList(@RequestParam String userName, @RequestParam String mobileNumber) {
		ModelAndView mav = new ModelAndView("userdetailslist");
		if (!(StringUtil.checkNullOrEmpty(userName) || StringUtil.checkNullOrEmpty(mobileNumber))) {
			mav.addObject("userDetailsList", userService.queryUserDetailsByUserNameOrMobile(userName, mobileNumber));
		} else {
			mav.addObject("userDetailsList", userService.queryAllUserList());
		}
		return mav;
	}
}
