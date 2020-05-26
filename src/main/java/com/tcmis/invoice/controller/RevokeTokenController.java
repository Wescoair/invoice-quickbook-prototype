package com.tcmis.invoice.controller;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.oauth2.client.OAuth2PlatformClient;
import com.intuit.oauth2.data.PlatformResponse;
import com.tcmis.invoice.authentication.OAuth2PlatformClientFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * Copy from https://github.com/IntuitDeveloper/OAuth2-JavaWithSDK/blob/master/src/main/java/com/intuit/developer/sampleapp/oauth2/controller/RevokeTokenController.java
 * @author dderose
 *
 */
@Slf4j
@RestController
public class RevokeTokenController {
	@Autowired
	OAuth2PlatformClientFactory factory;
	
    /**
     * Call to revoke tokens 
     * 
     * @param session
     * @return
     */
	@ResponseBody
    @RequestMapping("/revokeToken")
    public String revokeToken(HttpSession session) {
        try {
        	OAuth2PlatformClient client  = factory.getOAuth2PlatformClient();
        	String refreshToken = (String)session.getAttribute("refresh_token");
        	PlatformResponse response  = client.revokeToken(refreshToken);
            log.info("raw result for revoke token request= " + response.getStatus());
            
            return new JSONObject().put("response", "Revoke successful").toString();
        } catch (Exception ex) {
        	log.error("Exception while calling revokeToken ", ex);
        	return new JSONObject().put("response","Failed").toString();
        }
    }
}