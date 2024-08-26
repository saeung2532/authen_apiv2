package com.br.api.services;

import reactor.core.publisher.Mono;

public interface TestService {
	
	String getTest();
	
	String getTestOtherService();
	
	Mono<String> getTestOtherServiceV2();
	
}
