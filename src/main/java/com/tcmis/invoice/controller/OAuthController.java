package com.tcmis.invoice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.intuit.oauth2.config.OAuth2Config;
import com.intuit.oauth2.config.Scope;
import com.intuit.oauth2.exception.InvalidRequestException;
import com.tcmis.invoice.authentication.OAuth2PlatformClientFactory;
import com.tcmis.invoice.configuration.properties.ApplicationProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class OAuthController {
	@Autowired
	OAuth2PlatformClientFactory factory;
	
	@Autowired
	ApplicationProperties applicationProperties;
	
	@RequestMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/connectToQuickbooks")
	public View  connect(HttpSession session) {
		OAuth2Config oauth2Config = factory.getOAuth2Config();

		String csrf = oauth2Config.generateCSRFToken();
		session.setAttribute("csrfToken", csrf);
		try {
			List<Scope> scopes = new ArrayList<>();
			scopes.add(Scope.Accounting);
			
			return new RedirectView(oauth2Config.prepareUrl(scopes,
																						applicationProperties.getQuickBooksOauth2RedirectUrl(),
																						csrf),
													true,
													true,
													false);
		} catch (InvalidRequestException e) {
			log.error("Exception calling connectToQuickbooks ", e);
		}
		return null;
	}
}