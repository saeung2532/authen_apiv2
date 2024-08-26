package com.br.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.ibm.jtopenlite.database.DatabaseException;

@RestControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String handlerMaxUploadSize(MaxUploadSizeExceededException e) {
		return "Maximum upload size exceeded";
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String handlerValidation(ValidationException e) {
		return e.getMessage();
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String handlerUserDuplicate(UserDuplicateException e) {
		return e.getMessage();
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<String> handleDatabaseException(DatabaseException ex) {
		return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		return new ResponseEntity<>("Generic exception caught globally", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(CustomServiceException.class)
	public ResponseEntity<String> handleCustomServiceException(CustomServiceException e) {
		return new ResponseEntity<>("Service exception caught globally", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CustomDataAccessException.class)
	public ResponseEntity<String> handleCustomDataAccessException(CustomDataAccessException e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data access error: " + e.getMessage());
	}

}
