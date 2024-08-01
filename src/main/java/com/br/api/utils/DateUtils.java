package com.br.api.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DateUtils {

	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyy");
	
	@Bean
	public String todayString() {
		return simpleDateFormat.format(new Date());
		
	}
	
}
