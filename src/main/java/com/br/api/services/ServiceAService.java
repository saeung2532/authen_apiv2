package com.br.api.services;

import org.springframework.beans.factory.annotation.Qualifier;

//In Service A

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

@Service
public class ServiceAService {

	private RestTemplate restTemplate;
	private Builder webClientBuilder;
	private final WebClient webClient;

	public ServiceAService(@Qualifier("serviceARestTemplate") RestTemplate restTemplate,
			WebClient.Builder webClientBuilder) {
		this.restTemplate = restTemplate;
		this.webClientBuilder = webClientBuilder;
		this.webClient = webClientBuilder.baseUrl("http://localhost:8081/spring_api").build();
	}

	public String callServiceB() {
		return restTemplate.getForObject("/item", String.class);
	}

	// public Mono<String> callServiceB() {
	// return webClient.get()
	// .uri("/item")
	// .retrieve()
	// .bodyToMono(String.class);
	// }

}
