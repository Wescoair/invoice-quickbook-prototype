package com.tcmis.invoice.controller;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.oauth2.data.BearerTokenResponse;
import com.tcmis.invoice.authentication.OAuth2PlatformClientFactory;
import com.tcmis.invoice.helper.AuthenticationHelper;

import lombok.extern.slf4j.Slf4j;

/**Copy from https://github.com/IntuitDeveloper/OAuth2-JavaWithSDK/blob/master/src/main/java/com/intuit/developer/sampleapp/oauth2/controller/RefreshTokenController.java
 * @author dderose
 *
 */
@Slf4j
@RestController
public class RefreshTokenController {
	@Autowired
	OAuth2PlatformClientFactory factory;

	/**
	 * Call to refresh tokens
	 * 
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/refreshToken")
	public String refreshToken(HttpSession session) {
		try {
			BearerTokenResponse bearerTokenResponse = AuthenticationHelper.refreshToken(factory, session);
			
			return new JSONObject().put("access_token", bearerTokenResponse.getAccessToken())
													.put("refresh_token", bearerTokenResponse.getRefreshToken())
													.toString();
		} catch (Exception ex) {
			log.error("Exception while calling refreshToken ", ex);
			return new JSONObject().put("response", "Failed").toString();
		}
	}
}