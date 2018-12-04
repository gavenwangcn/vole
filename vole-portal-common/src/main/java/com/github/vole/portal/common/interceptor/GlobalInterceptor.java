package com.github.vole.portal.common.interceptor;

import com.github.vole.portal.common.fegin.MenuService;
import com.github.vole.portal.common.fegin.SettingService;
import com.github.vole.portal.common.fegin.UserService;
import com.github.vole.portal.common.util.BeanUtil;
import com.github.vole.portal.common.util.SecurityContextUtil;
import com.github.vole.portal.common.vo.SysSettingMenuVO;
import com.github.vole.portal.common.vo.SysSettingVO;
import com.github.vole.portal.common.vo.SysUserVO;
import com.github.vole.portal.common.vo.TreeMenu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 全局拦截器 获取装饰器信息
 */
@Slf4j
public class GlobalInterceptor extends HandlerInterceptorAdapter {

    private final static String SYS_NAME = "systemName";

    private final static String SYS_SUB_NAME = "systemSubName";

    private final static String SYS_GLOBAL_KEY = "systemGlobalKey";

    @Autowired
    Environment environment;


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            List<SysSettingMenuVO> SysSettingMenuVOs= null;
            /**
             * 加载全局非登录访问常量
             */
            SysSettingVO sys = BeanUtil.getBean(SettingService.class).findSysByGlobalKey(getApplicationId());
            if (sys != null) {
                SysSettingMenuVOs = BeanUtil.getBean(SettingService.class).findSysMenusById(sys.getId());
                request.setAttribute(SYS_NAME, sys.getSysName());
                request.setAttribute(SYS_SUB_NAME, sys.getSysSubName());
                request.setAttribute(SYS_GLOBAL_KEY,sys.getSysGlobalKey());
            }
            /**
             * 保存登录信息
             */
            Integer userId = SecurityContextUtil.getUserId();
            SysUserVO me = null;
            if (userId != null) {
                me = BeanUtil.getBean(UserService.class).findUserById(userId.toString());
            }
            if (me == null) {
                return true;
            }
            me.setPassword("");
            request.setAttribute("me", me);
            /**
             * 资源和当前选中菜单
             */
            String res = request.getParameter("p");
            if (!StringUtils.isEmpty(res)) {
                request.setAttribute("res", res);
            }
            String cur = request.getParameter("t");
            if (!StringUtils.isEmpty(cur)) {
                request.setAttribute("cur", cur);
            }
            if(SysSettingMenuVOs==null){
                return true;
            }
            List<TreeMenu> treeMenuVOs = new ArrayList<TreeMenu>();
            /**
             * 获取当前用户的菜单
             */
            List<TreeMenu> treeMenus = BeanUtil.getBean(MenuService.class).findTreeMenusByUserId(me.getUserId().toString());

            SysSettingMenuVOs.forEach(input->{
                treeMenus.forEach(menu->{
                    if(menu.getSysMenu().getId().toString().equals(input.getMenuId().toString())){
                        treeMenuVOs.add(menu);
                    }
                });
            });
            request.setAttribute("treeMenus", treeMenuVOs);
        }

        /**
         * 通过拦截
         */
        return true;
    }

    private String getApplicationId(){
        String name = environment.getProperty("spring.application.name");
        return StringUtils.hasText(name) ? name : "application";
    }
}
