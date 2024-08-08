package com.br.api.securities;

import static com.br.api.securities.SecurityConstants.HEADER_REQUEST_UUID;
import static com.br.api.securities.SecurityConstants.MDC_UUID_KEY;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class MDCClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		// Add the UUID to the request headers
		String uuid = MDC.get(MDC_UUID_KEY);

		System.out.println("intercept uuid: " + uuid);

		if (uuid != null) {
			request.getHeaders().add(HEADER_REQUEST_UUID, uuid);
		} else {
			request.getHeaders().add(HEADER_REQUEST_UUID, UUID.randomUUID().toString());
			
		}

		// Proceed with the request
		return execution.execute(request, body);
	}
}
