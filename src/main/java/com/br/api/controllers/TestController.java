package com.br.api.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.api.exceptions.CustomServiceException;
import com.br.api.services.TestService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/data")
public class TestController {
	
	protected static final Logger logger = LogManager.getLogger(TestController.class);
	private TestService testService;
	
	public TestController(TestService testService) {
		this.testService = testService;
	}
	
	@GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> test() {
		logger.info("Request: test");
		try {
			String company = testService.getTest();
			return new ResponseEntity<>(company, HttpStatus.OK);
			
		} catch (CustomServiceException e) {
            logger.error("Service error occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Service error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }


	}
	
	@GetMapping(value = "/testotherservice", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> testOtherService() {
		logger.info("Request: testotherservice");
		try {
			String item = testService.getTestOtherService();
			return new ResponseEntity<>(item, HttpStatus.OK);
			
		} catch (CustomServiceException e) {
            logger.error("Service error occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Service error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }


	}
	
	@GetMapping(value = "/testotherservicev2", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> testOtherServiceV2() {
		logger.info("Request: testotherservicev2");
		
		try {
			Mono<String> company = testService.getTestOtherServiceV2();
			return new ResponseEntity<>(company.toProcessor().block(), HttpStatus.OK);
			
		} catch (CustomServiceException e) {
            logger.error("Service error occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Service error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }


	}

}
