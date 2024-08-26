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
import com.br.api.services.CompanyService;

@RestController
@RequestMapping("/data")
public class CompanyController {

	protected static final Logger logger = LogManager.getLogger(CompanyController.class);

	private CompanyService companyService;

	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;

	}
	
	@GetMapping(value = "/company", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getCompany() {
		logger.info("Request: company");
		try {
			String company = companyService.getAllCompany();
			return new ResponseEntity<>(company, HttpStatus.OK);
			
		} catch (CustomServiceException e) {
            logger.error("Service error occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Service error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }


	}

}
