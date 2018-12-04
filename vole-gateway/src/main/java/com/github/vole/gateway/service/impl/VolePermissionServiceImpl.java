package com.github.vole.gateway.service.impl;

import com.github.vole.gateway.entity.vo.PermissionVO;
import com.github.vole.gateway.feign.RemotePermissionService;
import com.github.vole.gateway.service.VolePermissionService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Slf4j
@Service("volePermissionService")
public class VolePermissionServiceImpl implements VolePermissionService {
    @Autowired
    private RemotePermissionService remotePermissionService;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        //ele-mps options 跨域配置，现在处理是通过前端配置代理，不使用这种方式，存在风险
//        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod())) {
//            return true;
//        }
        Object principal = authentication.getPrincipal();
        List<SimpleGrantedAuthority> grantedAuthorityList = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        boolean hasPermission = false;

        if (principal != null) {
            if (CollectionUtils.isEmpty(grantedAuthorityList)) {
                log.warn("角色列表为空：{}", authentication.getPrincipal());
                return hasPermission;
            }

            Set<PermissionVO> permissionS = new HashSet<>();
            for (SimpleGrantedAuthority authority : grantedAuthorityList) {
                    Set<PermissionVO> permissionVOSet = remotePermissionService.findPermissionByRole(authority.getAuthority());
                    if (CollectionUtils.isNotEmpty(permissionVOSet)) {
                        permissionS.addAll(permissionVOSet);
                }
            }

            for (PermissionVO permission : permissionS) {
                if (permission!=null&&!StringUtils.isBlank(permission.getUrl())
                        && antPathMatcher.match(permission.getUrl(), request.getRequestURI())
                        ) {
                    if(permission.getMethod().equalsIgnoreCase("*")
                            || request.getMethod().equalsIgnoreCase(permission.getMethod())) {
                        hasPermission = true;
                        break;
                    }
                }
            }
        }
        return hasPermission;
    }
}
