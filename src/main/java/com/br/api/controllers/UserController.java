package com.br.api.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.api.exceptions.ValidationException;
import com.br.api.models.addon.User;
import com.br.api.services.UserService;


@RestController
@RequestMapping("/auth")
public class UserController {
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping()
	public List<User> getUser() {
		return userService.getUserAll();

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
		
//		boolean isAuthenticated = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
//        
//        if (isAuthenticated) {
//            // Generate and return token or other success response
//            return ResponseEntity.ok(new LoginResponse("Login successful", "some-token"));
//        } else {
//            // Return unauthorized status
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
//        }

	}
	
	@PostMapping(value = "/loginv2", produces = "application/json; charset=UTF-8")
    public void getData(HttpServletResponse response, @RequestBody UserRequest userRequest) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write(userService.loginDB2(userRequest));
		
		
		
		
		
    }

}
