package com.github.vole.passport.common.event;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.core.AuthenticationException;

public class PassportFailureEvent  extends AbstractAuthenticationFailureEvent {

    public PassportFailureEvent(AuthenticationException exception) {
        super(new FailedPassportClientAuthentication(), exception);
    }

}

class FailedPassportClientAuthentication extends AbstractAuthenticationToken {

    public FailedPassportClientAuthentication() {
        super(null);
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return "UNKNOWN";
    }

}
