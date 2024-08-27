package com.br.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.api.exceptions.CustomServiceException;
import com.br.api.services.UserAuthenService;

@RestController
@RequestMapping("/auth/v2")
public class UserAuthenController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserAuthenController.class);

	private UserAuthenService userAuthenService;

	public UserAuthenController(UserAuthenService userAuthenService) {
		this.userAuthenService = userAuthenService;

	}

	@PostMapping(value = "/logindb2", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> loginDB2(@RequestBody UserAuthenRequest userAuthenRequest) {
		logger.info("Request: loginDB2");
		try {
			return new ResponseEntity<>(userAuthenService.loginDB2(userAuthenRequest.getUsername(), userAuthenRequest.getPassword(),
					userAuthenRequest.getCompany(), userAuthenRequest.getApplication()), HttpStatus.OK);
			
		} catch (CustomServiceException e) {
            logger.error("Service error occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.error("Unexpected error occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

	}

	@GetMapping(value = "/checktoken")
	public ResponseEntity<String> checktoken(@RequestHeader("x-access-token") String token) {
		logger.info("Request: checktoken");
		try {
			return new ResponseEntity<>(userAuthenService.checkToken(token), HttpStatus.OK);
			
		} catch (CustomServiceException e) {
            logger.error("Service error occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.error("Unexpected error occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

	}
	
	@GetMapping(value = "/validate")
	public ResponseEntity<Boolean> getCompany(@RequestHeader("x-access-token") String token) {
		logger.info("Request: validate");
		try {
			return new ResponseEntity<>(userAuthenService.validateToken(token), HttpStatus.OK);
			
		} catch (CustomServiceException e) {
            logger.error("Service error occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        } catch (Exception e) {
            logger.error("Unexpected error occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }

	}

}
