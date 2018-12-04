package com.github.vole.portal.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.vole.portal.model.entity.SysMenu;
import com.github.vole.portal.mapper.SysMenuMapper;
import com.github.vole.portal.model.vo.TreeMenuAllowAccess;
import com.github.vole.portal.service.ISysMenuService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * SysMenu 表数据服务层接口实现类
 *
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Override
    public List<TreeMenuAllowAccess> selectTreeMenuAllowAccessByMenuIdsAndPid(
            final List<String> menuIds, String pid) {
        QueryWrapper<SysMenu> ew = new QueryWrapper<SysMenu>();
        ew.orderByAsc("sort");
        ew.eq("parent_id", pid);
        List<SysMenu> sysMenus = this.list(ew);

        List<TreeMenuAllowAccess> treeMenuAllowAccesss = new ArrayList<TreeMenuAllowAccess>();
        for(SysMenu sysMenu : sysMenus){
            TreeMenuAllowAccess treeMenuAllowAccess = new TreeMenuAllowAccess();
            treeMenuAllowAccess.setSysMenu(sysMenu);
            /**
             * 是否有权限
             */
            if(menuIds.contains(sysMenu.getId().toString())){
                treeMenuAllowAccess.setAllowAccess(1);
            }
            /**
             * 子节点
             */
            if(sysMenu.getDeep() < 3){
                treeMenuAllowAccess.setChildren(selectTreeMenuAllowAccessByMenuIdsAndPid(menuIds,sysMenu.getId().toString()));
            }
            treeMenuAllowAccesss.add(treeMenuAllowAccess);
        }
        return treeMenuAllowAccesss;
    }
}