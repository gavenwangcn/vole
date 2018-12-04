package com.github.vole.passport.common.details;

import com.github.vole.common.constants.CommonConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class PassportUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;
    private Integer userId;
    private String username;
    private String password;
    private String status;
    private List<GrantedAuthority>  roleList;

    public PassportUserDetails() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !StringUtils.equals(CommonConstant.STATUS_LOCK, status);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return StringUtils.equals(CommonConstant.STATUS_NORMAL, status);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<GrantedAuthority> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<GrantedAuthority> roleList) {
        this.roleList = roleList;
    }

    public String getStatus() {
        return status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
