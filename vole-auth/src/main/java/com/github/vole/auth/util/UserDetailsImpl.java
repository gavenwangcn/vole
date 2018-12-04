package com.github.vole.auth.util;

import com.github.vole.auth.model.vo.MemberVO;
import com.github.vole.auth.model.vo.RoleVO;
import com.github.vole.common.constants.CommonConstant;
import com.github.vole.common.constants.SecurityConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    private Long userId;
    private String username;
    private String password;
    private String status;
    private List<RoleVO> roleVoList;

    public UserDetailsImpl() {

    }

    public UserDetailsImpl(MemberVO memberVo) {
        this.userId = memberVo.getMemberId();
        this.username = memberVo.getMembername();
        this.password = memberVo.getPassword();
        this.status = memberVo.getDelFlag();
        roleVoList = memberVo.getRoleVoList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (RoleVO roleVo : roleVoList) {
            authorityList.add(new SimpleGrantedAuthority(roleVo.getRoleCode()));
        }
        authorityList.add(new SimpleGrantedAuthority(SecurityConstants.BASE_ROLE));
        return authorityList;
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

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<RoleVO> getRoleVoList() {
        return roleVoList;
    }

    public void setRoleVoList(List<RoleVO> roleVoList) {
        this.roleVoList = roleVoList;
    }

    public String getStatus() {
        return status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
