package com.br.api.securities;

import static com.br.api.securities.SecurityConstants.CLAIMS_ROLE;
import static com.br.api.securities.SecurityConstants.HEADER_REQUEST_TOKEN;
import static com.br.api.securities.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.br.api.utils.JwtUtil;

import io.jsonwebtoken.Claims;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private JwtUtil jwtUtil;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String authorizationHeader = request.getHeader(HEADER_REQUEST_TOKEN);
		if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
			UsernamePasswordAuthenticationToken authentication = getAuthentication(authorizationHeader);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String jwt) {

		try {
			Claims claims = jwtUtil.validateToken(jwt);

			String username = claims.getSubject();
			if (username == null) {
				return null;
			}

			ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
			String roles = claims.get(CLAIMS_ROLE).toString();
			
			System.out.println("roles: " + roles);
			
			grantedAuthorities.add(new SimpleGrantedAuthority(roles));

			return new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
		
		return null;

	}


}
