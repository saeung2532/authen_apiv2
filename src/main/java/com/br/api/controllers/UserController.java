package com.br.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.api.exceptions.ValidationException;
import com.br.api.models.addon.User;
import com.br.api.models.addon.UserAuthen;
import com.br.api.services.UserAuthenService;
import com.br.api.services.UserService;

@RestController
@RequestMapping("/auth")
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;

	private UserService userService;

	private UserAuthenService userAuthenService;

	public UserController(UserService userService, UserAuthenService userAuthenService) {
		this.userService = userService;
		this.userAuthenService = userAuthenService;
	}

	@GetMapping()
	public List<User> getUser() {
		return userService.getUserAll();

	}

	@GetMapping("/appctl1")
	public UserAuthen getUserAuthen(@Valid @RequestBody UserRequest userRequest) {
		return userAuthenService.findAuthenByCTLUIDAndCTLCONOAndCTLCODE(userRequest.getUsername(),
				userRequest.getCompany(), userRequest.getApplication());

	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/register")
	public User register(@Valid @RequestBody UserRequest userRequest, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			bindingResult.getFieldErrors().stream().forEach(fieldError -> {
				throw new ValidationException(fieldError.getField() + ": " + fieldError.getDefaultMessage());
			});

		}

		return userService.register(userRequest);

	}

	@PostMapping("/logindb2")
	public ResponseEntity loginDB2(@RequestBody UserRequest userRequest) {
		return ResponseEntity.ok(userService.loginDB2(userRequest));

		// boolean isAuthenticated =
		// authService.authenticate(loginRequest.getUsername(),
		// loginRequest.getPassword());
		//
		// if (isAuthenticated) {
		// // Generate and return token or other success response
		// return ResponseEntity.ok(new LoginResponse("Login successful",
		// "some-token"));
		// } else {
		// // Return unauthorized status
		// return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username
		// or password");
		// }

	}
	
	@PostMapping("/logindb2v2")
	public ResponseEntity loginDB2V2(@RequestBody UserRequest userRequest) {
		return ResponseEntity.ok(userAuthenService.checkLoginDB2(userRequest.getUsername(), userRequest.getUsername(), userRequest.getCompany(), userRequest.getApplication()));

		// boolean isAuthenticated =
		// authService.authenticate(loginRequest.getUsername(),
		// loginRequest.getPassword());
		//
		// if (isAuthenticated) {
		// // Generate and return token or other success response
		// return ResponseEntity.ok(new LoginResponse("Login successful",
		// "some-token"));
		// } else {
		// // Return unauthorized status
		// return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username
		// or password");
		// }

	}

	// @PostMapping(value = "/loginv2", produces = "application/json;
	// charset=UTF-8")
	// public void getData(HttpServletResponse response, @RequestBody UserRequest
	// userRequest) throws IOException {
	// response.setContentType("application/json");
	// response.getWriter().write(userService.loginDB2(userRequest));
	//
	//
	//
	//
	//
	// }

	@PostMapping("/loginv2")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody UserRequest userRequest) throws Exception {
		String response = userService.loginDB2(userRequest);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/loginv3")
	public ResponseEntity<Void> login(@RequestBody UserRequest userRequest) {
		Authentication authenticationRequest = UsernamePasswordAuthenticationToken
				.unauthenticated(userRequest.getUsername(), userRequest.getPassword());

		System.out.println("authenticationRequest: " + authenticationRequest);
		Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);
		System.out.println("authenticationResponse: " + authenticationResponse);
		return null;
	}

}
