package com.br.api.exceptions;

public class UserDuplicateException extends RuntimeException {

	public UserDuplicateException(String username) {
		super("Username: " + username + " already exists.");
	}
	
}
