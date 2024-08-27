package com.br.api.securities;

import static com.br.api.securities.SecurityConstants.CLAIMS_ROLE;
import static com.br.api.securities.SecurityConstants.HEADER_REQUEST_TOKEN;
import static com.br.api.securities.SecurityConstants.HEADER_REQUEST_USER;
import static com.br.api.securities.SecurityConstants.HEADER_REQUEST_UUID;
import static com.br.api.securities.SecurityConstants.MDC_UUID_KEY;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.br.api.utils.JwtUtil;

import io.jsonwebtoken.Claims;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private static final Logger logger = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

	private JwtUtil jwtUtil;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String authorizationHeader = request.getHeader(HEADER_REQUEST_TOKEN);
		String requestUuid = request.getHeader(HEADER_REQUEST_UUID);
		String userId = request.getHeader(HEADER_REQUEST_USER);

		try {
			if (requestUuid != null) {
				// Use the existing UUID from the request header
				MDC.put(MDC_UUID_KEY, requestUuid);
			} else {
				// Generate a new UUID if not present
				MDC.put(MDC_UUID_KEY, UUID.randomUUID().toString());
			}

			// if (authorizationHeader != null &&
			// authorizationHeader.startsWith(TOKEN_PREFIX)) {
			if (authorizationHeader != null) {
				UsernamePasswordAuthenticationToken authentication = getAuthentication(authorizationHeader);

				// Set the authentication in the security context
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			
			// logger.debug("MDCFilter user, uuid: {} {}", userId, requestUuid);

			chain.doFilter(request, response);

		} finally {
			// Clean up MDC
			MDC.remove(MDC_UUID_KEY);
		}

	}

	private UsernamePasswordAuthenticationToken getAuthentication(String jwt) {

		try {
			Claims claims = jwtUtil.validateToken(jwt);
			logger.debug("JWTAuthorizationFilter true: {}", claims);

			String username = claims.getSubject();
			if (username == null) {
				return null;
			}

			ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
			String roles = claims.get(CLAIMS_ROLE).toString();
			grantedAuthorities.add(new SimpleGrantedAuthority(roles));
			return new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);

		} catch (Exception e) {
			logger.error("JWTAuthorizationFilter false: {}", e.getMessage());
		}

		return null;

	}

}
