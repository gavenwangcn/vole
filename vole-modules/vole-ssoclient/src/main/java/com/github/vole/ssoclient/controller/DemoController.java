package com.github.vole.ssoclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * demo controller
 */
@RestController
public class DemoController {

//    @Autowired
//    private OAuth2RestOperations restTemplate;

    @GetMapping("/user")
    public Authentication user(Authentication authentication) {
        return authentication;
    }

    @GetMapping("/demo")
    public String demo(){
        return "this is sso client demo";
    }

    @RequestMapping("/relay")
    public String relay() {
        SecurityContextHolder.getContext().getAuthentication();
        //ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:7001/authentication/user", String.class);
       // return "Success! (" + response.getBody() + ")";
        return "success";
    }
}
