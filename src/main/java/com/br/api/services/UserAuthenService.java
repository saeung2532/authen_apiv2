package com.br.api.services;

import com.br.api.models.addon.UserAuthen;

public interface UserAuthenService {
	
	String loginDB2(String username, String password, Integer company, String application);
	
	String checkToken(String token);
	
	Boolean validateToken(String token);
	
	UserAuthen findAuthenByCTLUIDAndCTLCONOAndCTLCODE(String username, Integer company, String application);
	
}
