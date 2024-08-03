package com.br.api.services;

import com.br.api.models.addon.UserAuthen;

public interface UserAuthenService {
	
	String checkLoginDB2(String username, String password, Integer company, String application);
	
	Boolean checkToken();
	
	UserAuthen findAuthenByCTLUIDAndCTLCONOAndCTLCODE(String username, Integer company, String application);
	
}
