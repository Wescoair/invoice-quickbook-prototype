package com.tcmis.invoice.helper;

import javax.servlet.http.HttpSession;

import com.intuit.oauth2.client.OAuth2PlatformClient;
import com.intuit.oauth2.data.BearerTokenResponse;
import com.intuit.oauth2.exception.OAuthException;
import com.tcmis.invoice.authentication.OAuth2PlatformClientFactory;

public class AuthenticationHelper {
	public static BearerTokenResponse refreshToken(OAuth2PlatformClientFactory factory, HttpSession session) throws OAuthException {
		OAuth2PlatformClient client = factory.getOAuth2PlatformClient();
		String refreshToken = (String) session.getAttribute("refresh_token");

		BearerTokenResponse bearerTokenResponse = client.refreshToken(refreshToken);
		session.setAttribute("access_token", bearerTokenResponse.getAccessToken());
		session.setAttribute("refresh_token", bearerTokenResponse.getRefreshToken());
		
		return bearerTokenResponse;
	}
}