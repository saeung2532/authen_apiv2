package com.br.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.api.utils.DateUtils;

@RestController
public class DemoController {
	
	DateUtils dateUtils;
	 
	public DemoController(DateUtils dateUtils) {
		this.dateUtils = dateUtils;
	}
	
	@GetMapping("/")
	String getToday() {
		
		DemoController demoController = new DemoController(null);
		return demoController.toString();
//		return dateUtils.todayString();
	}

}
