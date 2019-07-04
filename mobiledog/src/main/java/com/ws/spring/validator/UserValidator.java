package com.ws.spring.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ws.spring.dto.UserDto;
import com.ws.spring.model.UserDetails;
import com.ws.spring.repository.UserRepository;

@Component
public class UserValidator implements Validator {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean supports(Class<?> aClass) {
		return UserDetails.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		UserDto userDto = (UserDto) o;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
		String userName = userDto.getUsername();
		if (userName.length() < 6 || userName.length() > 32) {
			errors.rejectValue("username", "Size.userForm.username");
		}
		UserDetails userDetails = userRepository.findUserDetailsByUserName(userName);
		if (userDetails != null) {
			errors.rejectValue("username", "Duplicate.userForm.username");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
		String password = userDto.getPassword();
		if (password.length() < 8 || password.length() > 32) {
			errors.rejectValue("password", "Size.userForm.password");
		}

		if (!password.equals(userDetails.getPassword())) {
			errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
		}
	}
}
