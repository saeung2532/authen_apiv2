package com.br.api.repositories.addon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.api.models.addon.UserAuthen;


@Repository
public interface UserAuthenRepository extends JpaRepository<UserAuthen, String> {
	UserAuthen findAuthenByCTLUIDAndCTLCONOAndCTLCODE(String username, Integer company, String application);

}
