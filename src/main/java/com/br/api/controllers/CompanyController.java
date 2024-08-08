package com.br.api.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.api.services.CompanyService;

@RestController
@RequestMapping("/data")
public class CompanyController {
	
	protected static final Logger logger = LogManager.getLogger(CompanyController.class);
	
	private CompanyService companyService;

	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	
	}
	
	@GetMapping("/perform")
    public String performAction() {
        logger.info("Request received in Controller");
        try {
            String result = null;
            logger.info("Response from Service: {}", result);
            return result;
        } catch (Exception e) {
            logger.error("Exception in Controller: {}", e.getMessage(), e);
            return "Error occurred";
        }
    }
	
	@GetMapping("/allcompany")
	public ResponseEntity<?> getAllCompany() {
		logger.info("Request: getAllCompany");
//		return companyService.getAllCompany();
		return new ResponseEntity<>(companyService.getAllCompany(), HttpStatus.OK);
		
	}
	
	@GetMapping("/company")
	public ResponseEntity<?> getCompany() {
		logger.info("Request: getAllCompany");
//		return companyService.getAllCompany();
		return new ResponseEntity<>(companyService.getAllCompany(), HttpStatus.OK);
		
//		 try {
//	            return new ResponseEntity<>(companyService.getAllCompany(), HttpStatus.OK);
//	        } catch (ResourceNotFoundException e) {
//	            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//	        } catch (DatabaseException e) {
//	            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
//	        }

	}

}
