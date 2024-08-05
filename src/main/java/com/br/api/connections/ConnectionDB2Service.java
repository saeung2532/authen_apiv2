package com.br.api.connections;

import static com.br.api.securities.SecurityConstants.EXPIRATION_TIME;
import static com.br.api.securities.SecurityConstants.SECRET_KEY;
import static com.br.api.securities.SecurityConstants.TOKEN_PREFIX;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.br.api.utils.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class ConnectionDB2Service {

	private JwtUtil jwtUtil;

	public ConnectionDB2Service(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
		// TODO Auto-generated constructor stub
	}

	public String checkConnection(String username, String password) {
		JSONObject mJsonObj = new JSONObject();

		String jdbcClassName = "com.ibm.jtopenlite.database.jdbc.JDBCDriver";
		String url = "jdbc:jtopenlite://192.200.9.190";

		Connection conn = null;
		try {
			Class.forName(jdbcClassName);
			conn = DriverManager.getConnection(url, username, password);

			mJsonObj.put("result", "ok");
			mJsonObj.put("token", jwtUtil.createToken(username));
			mJsonObj.put("message", "Login successfully");

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

	public Connection ConnectionDB2(String username, String password) throws ClassNotFoundException, SQLException {
		String jdbcClassName = "com.ibm.jtopenlite.database.jdbc.JDBCDriver";
		String url = "jdbc:jtopenlite://192.200.9.190";
		Class.forName(jdbcClassName);

		return DriverManager.getConnection(url, username, password);

	}

	private String createToken(Claims claims) {
		return Jwts.builder().setClaims(claims)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	private String checkToken(String jwt) {

		Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwt.replace(TOKEN_PREFIX, "")).getBody();

		String username = claims.getSubject();
		if (username == null) {
			return null;
		}

		// ArrayList<String> roles = (ArrayList<String>) claims.get(CLAIMS_ROLE);
		// ArrayList<GrantedAuthority> grantedAuthorities = new
		// ArrayList<GrantedAuthority>();
		// if (roles != null) {
		// for (String role : roles) {
		// grantedAuthorities.add(new SimpleGrantedAuthority(role));
		// }
		// }

		return claims.toString();
	}

}
