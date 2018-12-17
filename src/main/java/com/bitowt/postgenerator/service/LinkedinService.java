package com.bitowt.postgenerator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.impl.LinkedInTemplate;
import org.springframework.social.linkedin.config.support.LinkedInApiHelper;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

@Service
public class LinkedinService {

    @Value("${spring.social.linkedin.app-id}")
    private String linkedinAppId;
    @Value("${spring.social.linkedin.app-secret}")
    private String linkedinSecret;

    @Autowired
    private LnAccessGrantContainer lnAccessGrantContainer;

    private String accessToken;

    private LinkedIn linkedIn;

    public String createLinkedinAuthorizationURL() {
        LinkedInConnectionFactory connectionFactory = new LinkedInConnectionFactory(linkedinAppId, linkedinSecret);
        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();
        params.setRedirectUri("http://localhost:8080/logged");
        params.setScope("r_basicprofile,w_share,r_emailaddress,rw_company_admin");
        return oauthOperations.buildAuthorizeUrl(params);
    }

    public void createLinkedinAccessToken(String code) {
        LinkedInConnectionFactory connectionFactory = new LinkedInConnectionFactory(linkedinAppId, linkedinSecret);
        AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(code, "http://localhost:8080/logged", null);
        lnAccessGrantContainer.setAccessGrant(accessGrant);
        accessToken = accessGrant.getAccessToken();
        linkedIn = new LinkedInTemplate(accessToken);
    }

    public void addPostTest()
    {
        linkedIn = new LinkedInTemplate(accessToken);
        linkedIn.groupOperations().createPost(6402952,"Test","Test");
        System.out.println(linkedIn.profileOperations().getUserProfile().getId());
    }
}
