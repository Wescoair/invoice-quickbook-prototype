package com.tcmis.invoice.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.intuit.oauth2.client.OAuth2PlatformClient;
import com.intuit.oauth2.data.BearerTokenResponse;
import com.intuit.oauth2.data.UserInfoResponse;
import com.intuit.oauth2.exception.OAuthException;
import com.intuit.oauth2.exception.OpenIdException;
import com.tcmis.invoice.authentication.OAuth2PlatformClientFactory;
import com.tcmis.invoice.configuration.properties.ApplicationProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * Copy from
 * https://github.com/IntuitDeveloper/OAuth2-JavaWithSDK/blob/master/src/main/java/com/intuit/developer/sampleapp/oauth2/controller/CallbackController.java
 * 
 * @author dderose
 *
 */
@Slf4j
@Controller
public class CallbackController {
	@Autowired
	OAuth2PlatformClientFactory factory;
	
	@Autowired
	ApplicationProperties applicationProperties;

	/**
	 * This is the redirect handler you configure in your app on
	 * developer.intuit.com The Authorization code has a short lifetime. Hence
	 * Unless a user action is quick and mandatory, proceed to exchange the
	 * Authorization Code for BearerToken
	 * 
	 * @param auth_code
	 * @param state
	 * @param realmId
	 * @param session
	 * @return
	 */
	@RequestMapping("/oauth2redirect")
	public String callBackFromOAuth(@RequestParam("code") String authCode,
														@RequestParam("state") String state,
														@RequestParam(value = "realmId", required = false) String realmId,
														HttpSession session) {
		log.info("inside oauth2redirect of sample");
		try {
			String csrfToken = (String) session.getAttribute("csrfToken");
			if (csrfToken.equals(state)) {
				session.setAttribute("realmId", realmId);
				session.setAttribute("auth_code", authCode);

				OAuth2PlatformClient client = factory.getOAuth2PlatformClient();
				String redirectUri = applicationProperties.getQuickBooksOauth2RedirectUrl();
				log.info("inside oauth2redirect of sample -- redirectUri " + redirectUri);
				BearerTokenResponse bearerTokenResponse = client.retrieveBearerTokens(authCode, redirectUri);

				session.setAttribute("access_token", bearerTokenResponse.getAccessToken());
				session.setAttribute("refresh_token", bearerTokenResponse.getRefreshToken());

				/*
				 * Update your Data store here with user's AccessToken and RefreshToken along
				 * with the realmId
				 * 
				 * However, in case of OpenIdConnect, when you request OpenIdScopes during
				 * authorization, you will also receive IDToken from Intuit. You first need to
				 * validate that the IDToken actually came from Intuit.
				 */
				if (StringUtils.isNotBlank(bearerTokenResponse.getIdToken())) {
					try {
						if (client.validateIDToken(bearerTokenResponse.getIdToken())) {
							log.info("IdToken is Valid");
							// get user info
							saveUserInfo(bearerTokenResponse.getAccessToken(), session, client);
						}
					} catch (OpenIdException e) {
						log.error("Exception validating id token ", e);
					}
				}

				return "callback";
			}
			log.info("csrf token mismatch ");
		} catch (OAuthException e) {
			log.error("Exception in callback handler ", e);
		}
		
		return null;
	}

	private void saveUserInfo(String accessToken, HttpSession session, OAuth2PlatformClient client) {
		// Ideally you would fetch the realmId and the accessToken from the data store
		// based on the user account here.
		try {
			UserInfoResponse response = client.getUserInfo(accessToken);

			session.setAttribute("sub", response.getSub());
			session.setAttribute("givenName", response.getGivenName());
			session.setAttribute("email", response.getEmail());
		} catch (Exception ex) {
			log.error("Exception while retrieving user info ", ex);
		}
	}
}