package com.tcmis.invoice.authentication;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intuit.oauth2.client.OAuth2PlatformClient;
import com.intuit.oauth2.config.Environment;
import com.intuit.oauth2.config.OAuth2Config;
import com.tcmis.invoice.configuration.properties.ApplicationProperties;

/**
 * Copy from https://github.com/IntuitDeveloper/OAuth2-JavaWithSDK/blob/master/src/main/java/com/intuit/developer/sampleapp/oauth2/client/OAuth2PlatformClientFactory.java
 * @author dderose
 *
 */
@Service
public class OAuth2PlatformClientFactory {
	@Autowired
	ApplicationProperties applicationProperties;

	OAuth2PlatformClient client;
	OAuth2Config oauth2Config;

	@PostConstruct
	public void init() {
		// intitialize a single thread executor, this will ensure only one thread
		// processes the queue
		oauth2Config = new OAuth2Config.OAuth2ConfigBuilder(applicationProperties.getQuickBooksOauth2ClientId(),
																								applicationProperties.getQuickBooksOauth2Secret())
																.callDiscoveryAPI(Environment.SANDBOX) // call discovery API to populate urls
																.buildConfig();
		client = new OAuth2PlatformClient(oauth2Config);
	}

	public OAuth2PlatformClient getOAuth2PlatformClient() {
		return client;
	}

	public OAuth2Config getOAuth2Config() {
		return oauth2Config;
	}
}