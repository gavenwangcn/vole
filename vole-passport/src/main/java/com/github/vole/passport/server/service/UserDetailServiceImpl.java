package com.github.vole.passport.server.service;


import com.github.vole.passport.common.details.PassportUserDetails;
import com.github.vole.passport.server.entity.vo.SysRoleVO;
import com.github.vole.passport.server.entity.vo.SysUserVO;
import com.github.vole.passport.server.fegin.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service("userDetailService")
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    private UserService userService;

    @Override
    public PassportUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserVO userVo = userService.findUserByUsername(username);
        if (userVo != null) {
            PassportUserDetails details = new PassportUserDetails();
            List<GrantedAuthority> authorityList = new ArrayList<>();
            List<SysRoleVO> roleVoList = userVo.getRoleVoList();
            for (SysRoleVO roleVo : roleVoList) {
                authorityList.add(new SimpleGrantedAuthority(roleVo.getRoleCode()));
            }
            //初始化userDetails
            details.setUserId(userVo.getUserId());
            details.setUsername(userVo.getLoginname());
            details.setPassword(userVo.getPassword());
            details.setStatus(userVo.getDelFlag());
            details.setRoleList(authorityList);
            return details;
        }
        throw new UsernameNotFoundException("用户名不存在");
    }
}
