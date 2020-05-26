package com.tcmis.invoice.configuration.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:test-data.properties")
public class ApplicationProperties {
	@Autowired
	private Environment env;

	public String getQuickBooksOauth2ClientId() {
		return env.getProperty("quickbooks.oauth2.client-id");
	}

	public String getQuickBooksOauth2Secret() {
		return env.getProperty("quickbooks.oauth2.secret");
	}
	
	public String getQuickBooksOauth2RedirectUrl() {
		return env.getProperty("quickbooks.oauth2.redirect-url");
	}
	
	public String getQuickBooksBaseUrl() {
		return env.getProperty("quickbooks.base-url");
	}
	
	public String getQuickBooksCompanyUrl() {
		return getQuickBooksBaseUrl() + "/v3/company";
	}
	
	public String getInvoiceSaveDirectory() {
		return env.getProperty("invoice.save-directory");
	}
}