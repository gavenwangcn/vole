package com.github.vole.passport.common.details;

import com.github.vole.passport.common.auth.PassportAuthentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class DetailsConvertPassportAuth {

    public static PassportAuthentication convert(PassportUserDetails details){
        Collection<? extends GrantedAuthority> authoritys =details.getAuthorities();
        PassportAuthentication passportAuthentication  = new PassportAuthentication(authoritys);
        passportAuthentication.setUserId(details.getUserId());
        passportAuthentication.setPrincipal(details.getUsername());
        return passportAuthentication;
    }
}
