package com.bitowt.postgenerator.service;

import com.bitowt.postgenerator.entity.FacebookUser;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import com.restfb.types.InstagramUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.*;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class FacebookService {

    @Value("${spring.social.facebook.appId}")
    private String facebookAppId;
    @Value("${spring.social.facebook.appSecret}")
    private String facebookSecret;

    @Autowired
    private AccessGrantContainer accessGrantContainer;

    private UsersConnectionRepository usersConnectionRepository;

    private  GraphApi graphApi;

    private String accessToken;

    private FacebookUser facebookUser;

    public String createFacebookAuthorizationURL() {
        FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookAppId, facebookSecret);
        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();
        params.setRedirectUri("http://localhost:8080/logged");
        params.setScope("public_profile,publish_pages,user_birthday,user_posts,user_photos,manage_pages,publish_to_groups");
        System.out.println(params.getScope());

        return oauthOperations.buildAuthorizeUrl(params);
    }

    public void createFacebookAccessToken(String code) {
        FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookAppId, facebookSecret);
        AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(code, "http://localhost:8080/logged", null);
        accessGrantContainer.setAccessGrant(accessGrant);
        accessToken = accessGrant.getAccessToken();
        System.out.println(accessToken);
    }

    public FacebookUser getFacebookUser() {
        Facebook facebook = new FacebookTemplate(accessToken);
        String[] field = {"name,birthday"};
        return facebook.fetchObject("me", FacebookUser.class, field);
    }

    public String getEmail() {
        Facebook facebook = new FacebookTemplate(accessToken);
        String field = "email";
        return facebook.fetchObject("me", String.class, field);
    }

    public void post() {
        Facebook facebook = new FacebookTemplate(accessToken);
        //MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        //map.set("message", "TestKurrrr");
        //map.set("access_token", accessToken);
        //facebook.post("me","feed",map);
        facebook.feedOperations().updateStatus("TESssstKurrrr");
    }
}
