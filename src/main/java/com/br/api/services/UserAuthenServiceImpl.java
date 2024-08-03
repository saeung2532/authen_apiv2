package com.br.api.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.br.api.connections.ConnectionDB2Service;
import com.br.api.models.addon.UserAuthen;
import com.br.api.repositories.addon.UserAuthenRepository;

@Service
public class UserAuthenServiceImpl implements UserAuthenService {

	private UserAuthenRepository userAuthenRepository;
	private ConnectionDB2Service connectionDB2Service;
	
	public UserAuthenServiceImpl(UserAuthenRepository userAuthenRepository, ConnectionDB2Service connectionDB2Service) {
		this.userAuthenRepository = userAuthenRepository;
		this.connectionDB2Service = connectionDB2Service;

	}

	@Override
	public UserAuthen findAuthenByCTLUIDAndCTLCONOAndCTLCODE(String username, Integer company, String application) {
		Optional<UserAuthen> userAuthen = Optional.ofNullable(userAuthenRepository.findAuthenByCTLUIDAndCTLCONOAndCTLCODE(username, company,  application));

		if (userAuthen.isPresent()) {
			UserAuthen existingUser = userAuthen.get();
			return existingUser;

		}

		return null;
	}

	@Override
	public String checkLoginDB2(String username, String password, Integer company, String application) {
		return connectionDB2Service.checkConnection(username, password);
	}

	@Override
	public Boolean checkToken() {
		// TODO Auto-generated method stub
		return null;
	}

}
