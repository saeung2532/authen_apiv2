package com.br.api.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "service-b-client", url = "http://localhost:8081/spring_api") // Replace with the actual URL of Service B
public interface ServiceBClient {

    @GetMapping("/item")
    String getDataFromServiceB();
}