package com.lordeluan.helpdesk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.lordeluan.helpdesk.service.DBService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService service;
	
	public void intanciaDB() {
		this.service.intanciaDB();
	}
}
