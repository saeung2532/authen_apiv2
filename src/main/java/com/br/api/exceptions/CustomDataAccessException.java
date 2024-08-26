package com.br.api.exceptions;

public class CustomDataAccessException extends RuntimeException {
	public CustomDataAccessException(String message, Throwable cause) {
		super(message, cause);
	}
}
