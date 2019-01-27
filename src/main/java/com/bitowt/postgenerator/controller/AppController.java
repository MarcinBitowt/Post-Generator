package com.bitowt.postgenerator.controller;

import com.bitowt.postgenerator.service.FacebookService;
import com.bitowt.postgenerator.service.InstagramService;
import com.bitowt.postgenerator.service.LinkedinService;
import com.bitowt.postgenerator.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.social.oauth1.OAuthToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


@RestController
public class AppController {

    @Autowired
    private FacebookService facebookService;

    @Autowired
    private TwitterService twitterService;

    @Autowired
    private LinkedinService linkedinService;

    @Autowired
    private InstagramService instagramService;

    @GetMapping("/authorizeFace")
    public String createFacebookAuthorization() {
        return facebookService.createFacebookAuthorizationURL();
    }

    @GetMapping("/authorizeLinked")
    public String createLinkedinAuthorization() {
        return linkedinService.createLinkedinAuthorizationURL();
    }

    @GetMapping("/authorizeTwitter")
    public String createTwitterAuthorization() {
        return twitterService.createTwitterAuthorizationURL();
    }

    @GetMapping("/authorizeInsta")
    public String createInstagramAuthorization() {
        return instagramService.createInstagramAuthorizationURL();
    }

    @GetMapping("/facebook")
    public void createFacebookAccessToken(@RequestParam("code") String code) {
        facebookService.createFacebookAccessToken(code);
    }

    @GetMapping("/linked")
    public void createLinkedinAccessToken(@RequestParam("code") String code) {
        linkedinService.createLinkedinAccessToken(code);
    }

    @GetMapping("/instagram")
    public void createInstagramAccessToken(@RequestParam("code") String code) {
        instagramService.createInstagramAccessToken(code);
    }

    @GetMapping("/twitter")
    public void createTwitterAccessToken(@RequestParam("oauth_token") OAuthToken oAuthToken, @RequestParam("oauth_verifier") String oAuthVerifier) {
        twitterService.createTwitterAccessToken(oAuthToken, oAuthVerifier);
    }


}
