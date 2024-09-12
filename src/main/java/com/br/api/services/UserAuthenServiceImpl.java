package com.br.api.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
			logger.error("validateToken: false {}" + e.getMessage());
			return false;
		}

	}

	@Override
	public String loginDB2(String username, String password, String company, String application) {
		logger.info("Service: loginDB2");
		Connection conn = null;
		try {
			conn = connectionDB2Service.dologin(username, password);

			String getCompany[] = company.split(" : ");
			String getCono = getCompany[0];
			String getDivi = getCompany[1];
			String getCompanyName = getCompany[2];

			String getAuthen = getUserAuth(getCono, getDivi, application, username);
			logger.debug("getAuthen: {}", getAuthen);

			jsonObject.put("result", "ok");
			jsonObject.put("token", jwtUtil.createToken(company, username, getAuthen));
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

	private String getUserAuth(String cono, String divi, String app, String username) {
		logger.info("getUserAuth");

		Connection conn = null;
		Statement stmt = null;
		try {
			conn = connectionDB2Service.doConnect();
			stmt = conn.createStatement();

			String query = "SELECT CTL_SEQ, CTL_REM \n"
					+ "FROM BRLDTA0100.APPCTL1 a, BRLDTA0100.STAFFLIST b  \n"
					+ "WHERE CTL_CONO = '" + cono + "'\n"
					+ "AND CTL_CODE = '" + app + "' \n"
					+ "AND CTL_UID = '" + username + "' \n"
					+ "AND b.ST_N6L3 = a.CTL_UID \n"
					+ "AND b.ST_STS = '20' \n"
					+ "GROUP BY CTL_SEQ, CTL_REM \n"
					+ "ORDER BY CTL_SEQ";
			logger.debug(query);
			ResultSet mRes = stmt.executeQuery(query);

			while (mRes.next()) {
				return mRes.getString("CTL_REM").trim();
			}

		} catch (SQLException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}

		}

		return "US";

	}

}
