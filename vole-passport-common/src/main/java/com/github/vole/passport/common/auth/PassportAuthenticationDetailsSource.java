package com.github.vole.passport.common.auth;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;

public class PassportAuthenticationDetailsSource extends WebAuthenticationDetailsSource
        {
    @Override
    public PassportAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new PassportAuthenticationDetails(context);
    }
}
