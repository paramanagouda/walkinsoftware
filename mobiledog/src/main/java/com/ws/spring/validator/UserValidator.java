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
		if (userDto.getUsername().length() < 6 || userDto.getUsername().length() > 32) {
			errors.rejectValue("username", "Size.userForm.username");
		}
		UserDetails userDetails = userRepository.findUserDetailsByUserName(userDto.getUsername());
		if (userDetails != null) {
			errors.rejectValue("username", "Duplicate.userForm.username");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
		if (userDto.getPassword().length() < 8 || userDto.getPassword().length() > 32) {
			errors.rejectValue("password", "Size.userForm.password");
		}

		if (!userDto.getPassword().equals(userDetails.getPassword())) {
			errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
		}
	}
}
