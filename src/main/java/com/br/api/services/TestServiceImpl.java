package com.br.api.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import com.br.api.daos.TestDao;
import com.br.api.exceptions.CustomServiceException;

import reactor.core.publisher.Mono;

@Service
public class TestServiceImpl implements TestService {

	protected static final Logger logger = LogManager.getLogger(TestServiceImpl.class);

	private TestDao testDao;
	private RestTemplate restTemplate;
	private Builder webClientBuilder;
	private final WebClient webClient;

	public TestServiceImpl(TestDao testDao, @Qualifier("serviceARestTemplate") RestTemplate restTemplate,
			WebClient.Builder webClientBuilder) {
		this.testDao = testDao;
		this.restTemplate = restTemplate;
		this.webClientBuilder = webClientBuilder;
		this.webClient = webClientBuilder.baseUrl("http://localhost:8081/spring_api").build();
	}

	@Override
	public String getTest() {
		logger.info("Service: getTest");
		try {
			return testDao.getTest();

		} catch (DataAccessException e) {
			logger.error("Data access error occurred in service layer: {}", e.getMessage());
			throw new CustomServiceException("Failed to perform operation", e);
		} catch (Exception e) {
			logger.error("Unexpected error in performOperation: {}", e.getMessage());
			throw new CustomServiceException("Unexpected error in service layer", e);
		}

	}

	@Override
	public String getTestOtherService() {
		return restTemplate.getForObject("/item", String.class);
	}
	
	@Override
	public Mono<String> getTestOtherServiceV2() {
		return webClient.get()
				.uri("/auth")
				.retrieve()
				.bodyToMono(String.class);
	}

}
