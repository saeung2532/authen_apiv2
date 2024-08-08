package com.br.api.configs;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.br.api.securities.MDCClientHttpRequestInterceptor;

@Configuration
public class RestTemplateConfig {

	@Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        // Add the interceptor to the RestTemplate
        restTemplate.setInterceptors(Collections.singletonList(new MDCClientHttpRequestInterceptor()));
        return restTemplate;
    }
	
	@Bean
    public RestTemplate serviceARestTemplate() {
        DefaultUriBuilderFactory uriFactory = new DefaultUriBuilderFactory("http://localhost:8081/spring_api");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(uriFactory);
        restTemplate.setInterceptors(Collections.singletonList(new MDCClientHttpRequestInterceptor()));
        return restTemplate;
    }
	
	
}