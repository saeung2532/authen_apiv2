package com.br.api.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.api.controllers.UserRequest;
import com.br.api.exceptions.UserDuplicateException;
import com.br.api.models.addon.User;
import com.br.api.repositories.addon.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private AuthenticationManager authenticationManager;

	final private UserRepository userRepository;
	final private BCryptPasswordEncoder bcryptPasswordEncoder;
//	private AuthenticationManager authenticationManager;

	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bcryptPasswordEncoder
//			, AuthenticationManager authenticationManager
			) {
		this.userRepository = userRepository;
		this.bcryptPasswordEncoder = bcryptPasswordEncoder;
//		this.authenticationManager = authenticationManager;
	}

	@Override
	public User register(UserRequest userRequest) {
		User user = userRepository.findUserByUsername(userRequest.getUsername());

		if (user == null) {
			user = new User()
					.setUsername(userRequest.getUsername())
					.setPassword(bcryptPasswordEncoder.encode(userRequest.getPassword()))
					.setRole(userRequest.getRole());

			return userRepository.save(user);
		}

		throw new UserDuplicateException(user.getUsername());

	}

	@Override
	public User findUserByUsername(String username) {
		Optional<User> user = Optional.ofNullable(userRepository.findUserByUsername(username));

		if (user.isPresent()) {
			User existingUser = user.get();
			return existingUser;

		}

		return null;
	}

	@Override
	public List<User> getUserAll() {
		return userRepository.findAll();
	}

	@Override
	public String loginDB2(UserRequest userRequest) {

		// JSONObject mJsonObj = new JSONObject();

		String jdbcClassName = "com.ibm.jtopenlite.database.jdbc.JDBCDriver";
		String url = "jdbc:jtopenlite://192.200.9.190";

		Connection conn = null;
		try {
			Class.forName(jdbcClassName);
			conn = DriverManager.getConnection(url, userRequest.getUsername(),
					userRequest.getPassword());

			return "true";

		} catch (Exception e) {
			return e.getMessage().equals("java.io.IOException: Bad return code from signon info: 0x20002")
					? "java.io.IOException: Password has expired, please reset password."
					: e.getMessage();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				return e.getMessage();
			}

		}

	}

	@Override
	public List<User> loginM3() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> loginEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> checkLogin() {
		// TODO Auto-generated method stub
		return null;
	}

}
