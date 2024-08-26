package com.br.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.api.services.UserAuthenService;

@RestController
@RequestMapping("/auth/v2")
public class UserAuthenController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserAuthenController.class);

	private UserAuthenService userAuthenService;

	public UserAuthenController(UserAuthenService userAuthenService) {
		this.userAuthenService = userAuthenService;

	}

	@PostMapping("/logindb2")
	public ResponseEntity<String> loginDB2(@RequestBody UserAuthenRequest userAuthenRequest) {
		return ResponseEntity
				.ok(userAuthenService.loginDB2(userAuthenRequest.getUsername(), userAuthenRequest.getPassword(),
						userAuthenRequest.getCompany(), userAuthenRequest.getApplication()));

	}

	@GetMapping("/checktoken")
	public ResponseEntity<String> checktoken(@RequestHeader("x-access-token") String token) {
		return ResponseEntity.ok(userAuthenService.checkToken(token));

	}

	@GetMapping("/validate")
	public ResponseEntity<Boolean> validateToken(@RequestParam String token) {
		return ResponseEntity.ok(userAuthenService.validateToken(token));
		
	}

}
