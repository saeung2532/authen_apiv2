package com.br.api.repositories.addon;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.api.models.addon.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);

}
