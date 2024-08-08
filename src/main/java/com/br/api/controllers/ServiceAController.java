package com.br.api.controllers;

//In Service A

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.api.services.ServiceAService;

@RestController
public class ServiceAController {

 private final ServiceAService serviceAService;

 public ServiceAController(ServiceAService serviceAService) {
     this.serviceAService = serviceAService;
 }

 @GetMapping("/api/service-a/call-service-b")
 public String callServiceB() {
     return serviceAService.callServiceB();
//	 return null;
 }
}
