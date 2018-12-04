package com.github.vole.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.vole.portal.model.entity.SysMenu;
import com.github.vole.portal.model.vo.TreeMenuAllowAccess;

import java.util.List;

/**
 *
 * SysMenu 表数据服务层接口
 *
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 获取指定用户拥有权限
     * @param  menuIds 该角色拥有的权限ID集合
     * @param  pid 菜单父ID
     */
    List<TreeMenuAllowAccess> selectTreeMenuAllowAccessByMenuIdsAndPid(List<String> menuIds, String pid);

}