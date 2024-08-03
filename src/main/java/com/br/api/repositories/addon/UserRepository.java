package com.br.api.repositories.addon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.api.models.addon.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User findUserByUsername(String username);

}
