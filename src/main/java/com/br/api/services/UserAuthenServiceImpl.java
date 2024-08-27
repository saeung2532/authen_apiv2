package com.br.api.services;

import java.sql.Connection;
import java.util.Optional;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.br.api.connections.ConnectionDB2Service;
import com.br.api.models.addon.UserAuthen;
import com.br.api.repositories.addon.UserAuthenRepository;
import com.br.api.utils.JwtUtil;

import io.jsonwebtoken.Claims;

@Service
public class UserAuthenServiceImpl implements UserAuthenService {

	private static final Logger logger = LoggerFactory.getLogger(UserAuthenServiceImpl.class);

	private UserAuthenRepository userAuthenRepository;
	private ConnectionDB2Service connectionDB2Service;
	private JwtUtil jwtUtil;
	private JSONObject jsonObject;

	public UserAuthenServiceImpl(UserAuthenRepository userAuthenRepository, ConnectionDB2Service connectionDB2Service,
			JwtUtil jwtUtil, JSONObject jsonObject) {
		this.userAuthenRepository = userAuthenRepository;
		this.connectionDB2Service = connectionDB2Service;
		this.jwtUtil = jwtUtil;
		this.jsonObject = jsonObject;

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
	public Boolean validateToken(String token) {
		logger.info("Service: validateToken");
		try {
			jwtUtil.validateToken(token);
			logger.debug("validateToken: true");
			return true;

		} catch (Exception e) {
			logger.error("validateToken: fase {}" + e.getMessage());
			return false;
		}

	}

	@Override
	public String loginDB2(String username, String password, Integer company, String application) {
		logger.info("Service: loginDB2");
		Connection conn = null;
		try {
			conn = connectionDB2Service.ConnectionDB2(username, password);
			
			jsonObject.put("result", "ok");
			jsonObject.put("token", jwtUtil.createToken(username));
			jsonObject.put("message", "Login successfully");

		} catch (Exception e) {
			jsonObject.put("result", "nok");
			jsonObject.put("message",
					e.getMessage().equals("java.io.IOException: Bad return code from signon info: 0x20002")
							? "java.io.IOException: Password has expired, please reset password."
							: e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				jsonObject.put("result", "nok");
				jsonObject.put("message", e.getMessage());
			}

		}
		
		return jsonObject.toString();
	}

}
