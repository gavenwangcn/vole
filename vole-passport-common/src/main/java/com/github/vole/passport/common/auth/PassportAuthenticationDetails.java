package com.github.vole.passport.common.auth;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

public class PassportAuthenticationDetails extends WebAuthenticationDetails {

    public PassportAuthenticationDetails(HttpServletRequest request){
          super(request);
    }
}
