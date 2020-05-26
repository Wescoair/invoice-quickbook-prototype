package com.tcmis.invoice.configuration.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@PropertySource("file:${WESCO_CONFIG_LOC}/host.properties")
public class HostProperties {
	@Value("${database.username}")
	private String username;

	@Value("${database.password}")
	private String password;

	@Value("${database.url}")
	private String url;

	@Value("${webserver.isdevelopment:false}")
	private boolean isDevelopment;
}