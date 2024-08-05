package com.br.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.api.services.UserAuthenService;

@RestController
@RequestMapping("/auth/v2")
public class UserAuthenController {

	private UserAuthenService userAuthenService;

	public UserAuthenController(UserAuthenService userAuthenService) {
		this.userAuthenService = userAuthenService;

	}

	@PostMapping("/logindb2")
	public ResponseEntity<String> loginDB2(@RequestBody UserRequest userRequest) {
		return ResponseEntity.ok(userAuthenService.loginDB2(userRequest.getUsername(), userRequest.getPassword(),
				userRequest.getCompany(), userRequest.getApplication()));

	}

	@GetMapping("/checktoken")
	public ResponseEntity<String> checktoken(@RequestHeader("Authorization") String token) {
		return ResponseEntity.ok(userAuthenService.checkToken(token));

	}

}
