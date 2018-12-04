package com.github.vole.passport.common.cookie;

import com.github.vole.passport.common.auth.PassportAuthentication;
import com.github.vole.passport.common.token.PassportToken;
import com.github.vole.passport.common.tokenstore.PassportTokenStore;
import org.springframework.security.core.AuthenticationException;

public class DefaultPassportCookieServices implements PassportCookieServices{

    private PassportTokenStore passportTokenStore ;

    public void setPassportTokenStore(PassportTokenStore passportTokenStore) {
        this.passportTokenStore = passportTokenStore;
    }

    @Override
    public PassportAuthentication loadAuthentication(String cookieValue) throws AuthenticationException {
        PassportToken token = passportTokenStore.getToken(cookieValue);
        if(token!=null) {
            PassportAuthentication auth = (PassportAuthentication) token.getValue();
            return auth;
        }
        return null;
    }

    @Override
    public boolean removeAuthentication(String cookieValue) throws AuthenticationException {
        return passportTokenStore.deleteToken(cookieValue);
    }
}
