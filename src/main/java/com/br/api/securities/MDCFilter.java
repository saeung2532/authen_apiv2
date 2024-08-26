package com.br.api.securities;

import static com.br.api.securities.SecurityConstants.HEADER_REQUEST_UUID;
import static com.br.api.securities.SecurityConstants.MDC_UUID_KEY;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@WebFilter(urlPatterns = "/*")
@Order(1)
public class MDCFilter implements Filter {
	
	private static final Logger logger = LoggerFactory.getLogger(MDCClientHttpRequestInterceptor.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No initialization required
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestUuid = httpRequest.getHeader(HEADER_REQUEST_UUID);
//        logger.info("HttpRequest: {} ", requestUuid);    
        
        try {
            if (requestUuid != null) {
                // Use the existing UUID from the request header
                MDC.put(MDC_UUID_KEY, requestUuid);
            } else {
                // Generate a new UUID if not present
                MDC.put(MDC_UUID_KEY, UUID.randomUUID().toString());
            }
            
            logger.debug("HttpRequest: {} ", requestUuid);   
            
            chain.doFilter(request, response);
            
        } finally {
            // Clean up MDC
            MDC.remove(MDC_UUID_KEY);
        }
    }

    @Override
    public void destroy() {
        // No cleanup required
    }
}
