package com.br.api.exceptions;

public class CustomServiceException extends RuntimeException {
	public CustomServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
