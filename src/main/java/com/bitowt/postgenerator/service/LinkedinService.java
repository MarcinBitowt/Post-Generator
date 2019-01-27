package com.bitowt.postgenerator.service;

import com.bitowt.postgenerator.containers.LnAccessGrantContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.impl.LinkedInTemplate;
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

    private String accessToken="";

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
        System.out.println("LinkedIn accesss token: "+accessToken);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void addPostTest() {
        linkedIn = new LinkedInTemplate(accessToken);

        System.out.println("Profile id: " + linkedIn.profileOperations().getProfileId());
        System.out.println("Profile URL: " + linkedIn.profileOperations().getProfileUrl());
        System.out.println("E-mail: "+ linkedIn.profileOperations().getUserProfile().getEmailAddress());

        //linkedIn.groupOperations().createPost(0,"test","test");

        linkedIn.groupOperations().addCommentToPost("6480497138617978881","This comment is generated from code");
        //linkedIn.groupOperations().likePost("https://www.linkedin.com/feed/update/urn:li:activity:6480497138617978881");
        //linkedIn.groupOperations().createPost(6402952,"Test","Test");
        //linkedIn.profileOperations().getProfileById()
    }
}
