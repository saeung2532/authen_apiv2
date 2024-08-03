package com.br.api.connections;

import static com.br.api.securities.SecurityConstants.CLAIMS_ROLE;
import static com.br.api.securities.SecurityConstants.EXPIRATION_TIME;
import static com.br.api.securities.SecurityConstants.SECRET_KEY;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class ConnectionDB2Service {

	public String checkConnection(String username, String password) {
		JSONObject mJsonObj = new JSONObject();

		String jdbcClassName = "com.ibm.jtopenlite.database.jdbc.JDBCDriver";
		String url = "jdbc:jtopenlite://192.200.9.190";

		Connection conn = null;
		try {
			Class.forName(jdbcClassName);
			conn = DriverManager.getConnection(url, username, password);
			
			Claims claims = Jwts.claims().setSubject(username).setIssuer("aha").setAudience("aloha");
			claims.put(CLAIMS_ROLE, "admin");
			claims.put("value", "foo");
			
			mJsonObj.put("result", "ok");
			mJsonObj.put("token", createToken(claims));
			mJsonObj.put("message", "Login successfully");

		} catch (Exception e) {
//			return e.getMessage().equals("java.io.IOException: Bad return code from signon info: 0x20002")
//					? "java.io.IOException: Password has expired, please reset password."
//					: e.getMessage();
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", e.getMessage().equals("java.io.IOException: Bad return code from signon info: 0x20002")
					? "java.io.IOException: Password has expired, please reset password."
					: e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
//				return e.getMessage();
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", e.getMessage());
			}

		}
		
		return mJsonObj.toString();
	}
	
	private String createToken(Claims claims) {
		return Jwts.builder().setClaims(claims).setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

}
