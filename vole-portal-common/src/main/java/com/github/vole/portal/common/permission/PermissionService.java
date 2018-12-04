package com.github.vole.portal.common.permission;

import com.github.vole.passport.common.permission.AccessPermission;
import com.github.vole.portal.common.fegin.MenuService;
import com.github.vole.portal.common.vo.SysMenuVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
@Service
public class PermissionService implements AccessPermission {

    @Autowired
    private MenuService menuService;

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    public MenuService getMenuService() {
        return menuService;
    }

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        Object principal = authentication.getPrincipal();
        List<SimpleGrantedAuthority> grantedAuthorityList = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        boolean hasPermission = false;

        if (principal != null) {
            if (CollectionUtils.isEmpty(grantedAuthorityList)) {
                log.warn("角色列表为空：{}", authentication.getPrincipal());
                return hasPermission;
            }

            Set<SysMenuVO> menuVOS = new HashSet<>();
            for (SimpleGrantedAuthority authority : grantedAuthorityList) {
                Set<SysMenuVO> menuVOSet = menuService.findMenusByRole(authority.getAuthority());
                if (CollectionUtils.isNotEmpty(menuVOSet)) {
                    menuVOS.addAll(menuVOSet);
                }
            }

            for (SysMenuVO menuVO : menuVOS) {
                if (StringUtils.isNotBlank(menuVO.getResource())) {
                    String[] urls = StringUtils.split(menuVO.getResource(), ",");
                    for (int i = 0; i < urls.length; i++) {
                        if (antPathMatcher.match(urls[i], request.getRequestURI())){
                            hasPermission = true;
                            break;
                        }
                    }
                    if (hasPermission) break;
                }
            }
        }
        return hasPermission;
    }
}
