package com.bitowt.postgenerator.controller;

import com.bitowt.postgenerator.service.FacebookService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AppController {

    @Autowired
    private FacebookService facebookService;

    @GetMapping("/authorize")
    public String createFacebookAuthorization(){
        return facebookService.createFacebookAuthorizationURL();
    }

    @GetMapping("/facebook")
    public void createFacebookAccessToken(@RequestParam("code") String code){
        facebookService.createFacebookAccessToken(code);
    }
}
