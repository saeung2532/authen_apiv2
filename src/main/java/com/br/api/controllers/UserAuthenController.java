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
	public ResponseEntity loginDB2(@RequestBody UserRequest userRequest) {
		return ResponseEntity.ok(userAuthenService.checkLoginDB2(userRequest.getUsername(), userRequest.getUsername(),
				userRequest.getCompany(), userRequest.getApplication()));

	}

	@GetMapping("/checktoken")
	public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String token) {
		// if (token != null && token.startsWith("Bearer ")) {
		// String jwtToken = token.substring(7);
		// String username = jwtUtil.getUsernameFromToken(jwtToken);
		// if (username != null && jwtUtil.validateToken(jwtToken, username)) {
		// return ResponseEntity.ok(true);
		// }
		// }
		return ResponseEntity.ok(false);
	}

}
