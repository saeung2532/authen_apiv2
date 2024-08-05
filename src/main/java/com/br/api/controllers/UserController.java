package com.br.api.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.api.exceptions.ValidationException;
import com.br.api.models.addon.User;
import com.br.api.services.UserService;

@RestController
@RequestMapping("/auth")
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/register")
	public User register(@Valid @RequestBody UserRequest userRequest, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			bindingResult.getFieldErrors().stream().forEach(fieldError -> {
				throw new ValidationException(fieldError.getField() + ": " + fieldError.getDefaultMessage());
			});

		}

		return userService.register(userRequest);

	}

}
