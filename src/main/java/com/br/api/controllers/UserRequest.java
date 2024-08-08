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
public class UserRequest {
	
	@NotEmpty()
	@Size(min = 1, max = 100)
	private String username;
	
	@NotEmpty()
	@Length(min = 4, message = "The field must be at least {min} characters")
	private String password;
	
	@NotEmpty()
	private String role;


}
