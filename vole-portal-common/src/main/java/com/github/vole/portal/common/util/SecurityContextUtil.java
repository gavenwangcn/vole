package com.github.vole.portal.common.util;

import com.github.vole.passport.common.auth.PassportAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Slf4j
public class SecurityContextUtil {


    public static Integer getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            if (auth instanceof PassportAuthentication) {
                PassportAuthentication passportAuthentication = (PassportAuthentication) auth;
                return passportAuthentication.getUserId();
            }
            log.info("Auth is not PassportAuthentication Auth:" + auth.getPrincipal());
        }
        return null;
    }

    public static List<GrantedAuthority> getRoles() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            PassportAuthentication passportAuthentication = (PassportAuthentication) auth;
            List<GrantedAuthority> Authoritys = (List<GrantedAuthority>) passportAuthentication.getAuthorities();
            return Authoritys;
        }
        return null;
    }


}
