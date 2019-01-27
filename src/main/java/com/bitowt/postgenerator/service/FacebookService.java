package com.bitowt.postgenerator.service;

import com.bitowt.postgenerator.containers.FbAccessGrantContainer;
import com.bitowt.postgenerator.entity.FacebookUser;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.api.*;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class FacebookService {

    @Value("${spring.social.facebook.appId}")
    private String facebookAppId;
    @Value("${spring.social.facebook.appSecret}")
    private String facebookSecret;

    @Autowired
    private FbAccessGrantContainer fbAccessGrantContainer;

    private String accessToken="";

    public String createFacebookAuthorizationURL()  {
        FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookAppId, facebookSecret);
        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();
        params.setRedirectUri("http://localhost:8080/facebook");
        params.setScope("public_profile,publish_pages,user_birthday,user_posts,user_photos,manage_pages,publish_to_groups");
        return oauthOperations.buildAuthorizeUrl(params);
    }

    public void createFacebookAccessToken(String code) {
        FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookAppId, facebookSecret);
        AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(code, "http://localhost:8080/facebook", null);
        fbAccessGrantContainer.setAccessGrant(accessGrant);
        accessToken = accessGrant.getAccessToken();
        System.out.println("Facebook accesss token: "+accessToken);
    }

    @RequestMapping("/post")
    public void getFacebookUser() {
        Facebook facebook = new FacebookTemplate("EAAIsFN8DmMoBABsGmWE58fnjaz91RHtPisjB6a5PwaHKcvmNQJ3DWSheZBSnnBrfKNWK2WsZCScLPhNCqqbmLl4Tnz1EMTMPDjmtFoYmJMvZC7RZC4u14b0IZBBi5PJZCDPNxHOnUkh7DYU4S8FZCtNyWwPbOQPa4QZD");
        //String[] field = {"name,birthday"};
        facebook.feedOperations().updateStatus("TEST");
        //return facebook.fetchObject("me", FacebookUser.class, field);
    }

    public String getAccessToken() {
        return accessToken;
    }


}
