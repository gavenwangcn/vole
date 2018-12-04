package com.github.vole.passport.common.event;

import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.core.Authentication;

public class PassportSuccessEvent  extends AbstractAuthenticationEvent {

    public PassportSuccessEvent(Authentication authentication) {
        super(authentication);
    }
}
