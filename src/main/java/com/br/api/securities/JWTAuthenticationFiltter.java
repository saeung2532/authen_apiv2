package com.br.api.securities;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.br.api.controllers.UserRequest;
import com.br.api.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthenticationFiltter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private JwtUtil jwtUtil;

	public JWTAuthenticationFiltter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/auth/login", "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			UserRequest userRequest = new ObjectMapper().readValue(request.getInputStream(), UserRequest.class);

			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(),
					userRequest.getPassword(), new ArrayList<>()));
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		if (authResult.getPrincipal() != null) {
			org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult
					.getPrincipal();

			String username = user.getUsername();

			if (username != null && username.length() > 0) {
				Map<String, Object> responseJSON = new HashMap<>();
				responseJSON.put("token", jwtUtil.createToken(username));
				
				OutputStream out = response.getOutputStream();
				ObjectMapper mapper = new ObjectMapper();
				mapper.writerWithDefaultPrettyPrinter().writeValue(out, responseJSON);

				out.flush();
			}
		}
	}
	
//	@Override
//	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
//			Authentication authResult) throws IOException, ServletException {
//		if (authResult.getPrincipal() != null) {
//			org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult
//					.getPrincipal();
//
//			String username = user.getUsername();
//
//			if (username != null && username.length() > 0) {
//				Claims claims = Jwts.claims().setSubject(username).setIssuer("aha");
//
//				List<String> roles = new ArrayList<>();
//
//				user.getAuthorities().stream().forEach(authority -> roles.add(authority.getAuthority()));
//
//				claims.put(CLAIMS_ROLE, roles);
//				claims.put("value", "aha");
//
//				response.setContentType("application/json");
//				response.setCharacterEncoding("UTF-8");
//
//				Map<String, Object> responseJSON = new HashMap<>();
//
//				responseJSON.put("token", createToken(claims));
//
//				OutputStream out = response.getOutputStream();
//				ObjectMapper mapper = new ObjectMapper();
//				mapper.writerWithDefaultPrettyPrinter().writeValue(out, responseJSON);
//
//				out.flush();
//
//			}
//
//		}
//
//	}
//
//	private String createToken(Claims claims) {
//		return Jwts.builder().setClaims(claims)
//				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
//
//	}


}
