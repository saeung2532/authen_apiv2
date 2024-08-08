package com.br.api.configs;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.br.api.securities.CustomUserDetailsService;
import com.br.api.securities.JWTAuthenticationFiltter;
import com.br.api.securities.JWTAuthorizationFilter;
import com.br.api.utils.JwtUtil;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final CustomUserDetailsService customUserDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private JwtUtil jwtUtil;

	public SecurityConfig(
			CustomUserDetailsService customUserDetailsService,
			BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtil jwtUtil) {
		this.customUserDetailsService = customUserDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.jwtUtil = jwtUtil;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	UsernamePasswordAuthenticationFilter authenticationFilter() throws Exception {
		final UsernamePasswordAuthenticationFilter filter = new JWTAuthenticationFiltter(authenticationManager(),
				jwtUtil);
		filter.setAuthenticationManager(authenticationManager());
		return filter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests()
				 .antMatchers("/**").permitAll()
				.antMatchers(HttpMethod.GET, "/data/company").permitAll()
//				.antMatchers("/data/allcompany").permitAll()
				.antMatchers("/auth/register").permitAll()
				.antMatchers("/auth/v2/*").permitAll()
				.anyRequest().authenticated()
				.and().exceptionHandling()
				.authenticationEntryPoint((req, res, error) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED))
				.and().addFilter(authenticationFilter()).sessionManagement()
				.and().addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil)).sessionManagement()
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

}
