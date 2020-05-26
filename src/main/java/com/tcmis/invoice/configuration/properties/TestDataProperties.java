package com.tcmis.invoice.configuration.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:test-data.properties")
public class TestDataProperties {
	@Autowired
	private Environment env;
	
	public String getJSONData() {
		return env.getProperty("data.json");
	}
}
