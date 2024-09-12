package com.br.api.controllers;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAuthenRequest {

	@NotEmpty()
	@Size(min = 1, max = 20)
	private String username;

	@NotEmpty()
	@Length(min = 4, message = "The field must be at least {min} characters")
	private String password;

	@NotEmpty()
	private String company;

	@NotEmpty()
	private String application;

}