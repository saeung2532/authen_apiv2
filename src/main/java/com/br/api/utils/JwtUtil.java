package com.br.api.utils;

import static com.br.api.securities.SecurityConstants.CLAIMS_ROLE;
import static com.br.api.securities.SecurityConstants.EXPIRATION_TIME;
import static com.br.api.securities.SecurityConstants.SECRET_KEY;
import static com.br.api.securities.SecurityConstants.TOKEN_PREFIX;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtil {

	public String createToken(String username) {
		Claims claims = Jwts.claims().setSubject(username).setIssuer("aha").setAudience("aloha");
		claims.put(CLAIMS_ROLE, "admin");
		claims.put("value", "foo");
		return doCreateToken(claims, username);
	}

	private String doCreateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public Claims validateToken(String token) throws ExpiredJwtException, UnsupportedJwtException,
			MalformedJwtException, SignatureException, IllegalArgumentException {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
	}

}