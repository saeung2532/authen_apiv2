package com.br.api.services;

import java.sql.Connection;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.br.api.connections.ConnectionDB2Service;
import com.br.api.models.addon.UserAuthen;
import com.br.api.repositories.addon.UserAuthenRepository;
import com.br.api.utils.JwtUtil;

import io.jsonwebtoken.Claims;

@Service
public class UserAuthenServiceImpl implements UserAuthenService {

	private UserAuthenRepository userAuthenRepository;
	private ConnectionDB2Service connectionDB2Service;
	private JwtUtil jwtUtil;

	public UserAuthenServiceImpl(UserAuthenRepository userAuthenRepository, ConnectionDB2Service connectionDB2Service,
			JwtUtil jwtUtil) {
		this.userAuthenRepository = userAuthenRepository;
		this.connectionDB2Service = connectionDB2Service;
		this.jwtUtil = jwtUtil;

	}

	@Override
	public UserAuthen findAuthenByCTLUIDAndCTLCONOAndCTLCODE(String username, Integer company, String application) {
		Optional<UserAuthen> userAuthen = Optional.ofNullable(
				userAuthenRepository.findAuthenByCTLUIDAndCTLCONOAndCTLCODE(username, company, application));

		if (userAuthen.isPresent()) {
			UserAuthen existingUser = userAuthen.get();
			return existingUser;

		}

		return null;
	}

	public String checkLoginDB2(String username, String password, Integer company, String application) {
		return connectionDB2Service.checkConnection(username, password);
	}

	public String checkToken(String token) {
		JSONObject mJsonObj = new JSONObject();

		try {

			Claims validate = jwtUtil.validateToken(token);

			mJsonObj.put("result", "ok");
			mJsonObj.put("authen", true);
			mJsonObj.put("message", validate.toString());

		} catch (Exception e) {
			mJsonObj.put("result", "nok");
			mJsonObj.put("authen", false);
			mJsonObj.put("message", e.getMessage());

		}

		return mJsonObj.toString();
	}

	@Override
	public String loginDB2(String username, String password, Integer company, String application) {
		JSONObject mJsonObj = new JSONObject();

		Connection conn = null;
		try {
			conn = connectionDB2Service.ConnectionDB2(username, password);

		} catch (Exception e) {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message",
					e.getMessage().equals("java.io.IOException: Bad return code from signon info: 0x20002")
							? "java.io.IOException: Password has expired, please reset password."
							: e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", e.getMessage());
			}

		}

		return mJsonObj.toString();
	}

}
