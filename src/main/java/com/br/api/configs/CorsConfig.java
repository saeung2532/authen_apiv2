package com.br.api.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// registry.addMapping("/**");
		registry.addMapping("/**")
				.allowedOrigins("http://localhost", "http://127.0.0.1")  // Only allow localhost
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
				.allowedHeaders("*")
				.allowCredentials(true);
	}

//	@Component
//	public class UserAgentFilter implements Filter {
//
//	    @Override
//	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//	            throws IOException, ServletException {
//	        HttpServletRequest httpRequest = (HttpServletRequest) request;
//	        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//	        String userAgent = httpRequest.getHeader("User-Agent");
//
//	        if (userAgent != null && (userAgent.contains("Mozilla") || userAgent.contains("Chrome") || userAgent.contains("Safari") || userAgent.contains("Firefox"))) {
//	            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access from browsers is blocked");
//	            return;
//	        }
//
//	        chain.doFilter(request, response);
//	    }
//	}
	
}
