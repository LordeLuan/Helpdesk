package com.lordeluan.helpdesk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.lordeluan.helpdesk.service.DBService;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

	@Autowired
	private DBService service;

	@Override
	public void run(String... args) throws Exception {
		this.service.intanciaDB();
		
	}
}
