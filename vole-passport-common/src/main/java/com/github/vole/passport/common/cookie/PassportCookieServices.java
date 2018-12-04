package com.github.vole.passport.common.cookie;

import com.github.vole.passport.common.auth.PassportAuthentication;
import org.springframework.security.core.AuthenticationException;

public interface PassportCookieServices {

    PassportAuthentication loadAuthentication(String cookieValue) throws AuthenticationException;

    boolean removeAuthentication (String cookieValue) throws AuthenticationException;
}
