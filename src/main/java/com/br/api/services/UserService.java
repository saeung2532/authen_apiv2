package com.br.api.services;

import java.util.List;

import com.br.api.controllers.UserRequest;
import com.br.api.models.addon.User;

public interface UserService {
	
	String loginDB2(UserRequest userRequest);
	
	List<User> loginM3();
	
	List<User> loginEmail();
	
	List<User> checkLogin();
	
	List<User> getUserAll();
	
	User register(UserRequest userRequest);
	
	User findUserByUsername(String username);

}
