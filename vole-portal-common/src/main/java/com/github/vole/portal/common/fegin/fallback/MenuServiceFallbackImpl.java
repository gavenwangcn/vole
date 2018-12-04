package com.github.vole.portal.common.fegin.fallback;

import com.github.vole.portal.common.fegin.MenuService;
import com.github.vole.portal.common.vo.SysMenuVO;
import com.github.vole.portal.common.vo.TreeMenu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 菜单服务的fallback
 */
@Slf4j
@Service
public class MenuServiceFallbackImpl implements MenuService {

    @Override
    public List<TreeMenu> findTreeMenusByUserId(String userId) {
        log.error("调用{}异常:{}", "findTreeMenusByUserId", userId);
        return null ;
    }

    @Override
    public Set<SysMenuVO> findMenusByRole(String roleCode) {
        log.error("调用{}异常:{}", "findMenusByRole", roleCode);
        return null;
    }

}
