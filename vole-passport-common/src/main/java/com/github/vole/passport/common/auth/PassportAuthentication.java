package com.github.vole.passport.common.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PassportAuthentication extends AbstractAuthenticationToken {

    private Object principal;

    private Integer userId;


    public PassportAuthentication(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        super.setAuthenticated(true);
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setPrincipal(Object principal) {
        this.principal = principal;
    }

    public Integer getUserId() {
        return this.userId;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PassportAuthentication)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result;
        return result;
    }
}
