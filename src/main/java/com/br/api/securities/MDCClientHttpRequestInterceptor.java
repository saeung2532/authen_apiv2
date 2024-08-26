package com.br.api.securities;

import static com.br.api.securities.SecurityConstants.HEADER_REQUEST_UUID;
import static com.br.api.securities.SecurityConstants.MDC_UUID_KEY;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class MDCClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(MDCClientHttpRequestInterceptor.class);

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		// Add the UUID to the request headers
		String uuid = MDC.get(MDC_UUID_KEY);
//		logger.info("HttpRequestInterceptor: {} ", uuid);

		if (uuid != null) {
			request.getHeaders().add(HEADER_REQUEST_UUID, uuid);
		} else {
			request.getHeaders().add(HEADER_REQUEST_UUID, UUID.randomUUID().toString());

		}
		
		logger.debug("HttpRequestInterceptor: {} ", uuid);

		// Proceed with the request
		return execution.execute(request, body);
	}
}
