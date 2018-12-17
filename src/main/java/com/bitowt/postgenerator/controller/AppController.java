package com.bitowt.postgenerator.controller;

import com.bitowt.postgenerator.service.FacebookService;
import com.bitowt.postgenerator.service.LinkedinService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AppController {

    @Autowired
    private FacebookService facebookService;

    @Autowired
    private LinkedinService linkedinService;

    @GetMapping("/authorizeFace")
    public String createFacebookAuthorization(){
        return facebookService.createFacebookAuthorizationURL();
    }

    @GetMapping("/authorizeLinked")
    public String createLinkedinAuthorization(){
        return linkedinService.createLinkedinAuthorizationURL();
    }

    @GetMapping("/facebook")
    public void createFacebookAccessToken(@RequestParam("code") String code){
        facebookService.createFacebookAccessToken(code);
    }

    @GetMapping("/linked")
    public void createLinkedinAccessToken(@RequestParam("code") String code){
        linkedinService.createLinkedinAccessToken(code);
    }
}
