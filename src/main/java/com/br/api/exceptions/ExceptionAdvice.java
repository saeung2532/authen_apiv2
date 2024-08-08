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

//	@ExceptionHandler
//	@ResponseStatus(HttpStatus.NOT_FOUND)
//	String handlerProductNotFound(ProductNotFoundException ex) {
//		return ex.getMessage();
//	}

//	@ExceptionHandler
//	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//	String handlerStorageException(StorageException ex) {
//		return ex.getMessage();
//	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String handlerMaxUploadSize(MaxUploadSizeExceededException ex) {
		return "Maximum upload size exceeded";
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String handlerValidation(ValidationException ex) {
		return ex.getMessage();
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String handlerUserDuplicate(UserDuplicateException ex) {
		return ex.getMessage();
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<String> handleDatabaseException(DatabaseException ex) {
        return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
