package com.github.vole.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.vole.portal.mapper.SysSettingMenuMapper;
import com.github.vole.portal.model.entity.SysSettingMenu;
import com.github.vole.portal.service.ISysSettingMenuService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysSettingMenuServiceImpl extends ServiceImpl<SysSettingMenuMapper, SysSettingMenu> implements ISysSettingMenuService {
    @Override
    public void addAuth(String sysId, String[] menuIds) {
        /**
         * 删除原有权限
         */
        this.remove(new QueryWrapper<SysSettingMenu>().eq("sys_id",sysId));
        /**
         * 重新授权
         */
        if(ArrayUtils.isNotEmpty(menuIds)){
            for(String menuId : menuIds){
                SysSettingMenu sysSettingMenu = new SysSettingMenu();
                sysSettingMenu.setSysId(new Integer(sysId));
                sysSettingMenu.setMenuId(new Integer(menuId));
                this.save(sysSettingMenu);
            }
        }
    }

    @Override
    public List<SysSettingMenu> selectBySetting(String sysId) {
        QueryWrapper<SysSettingMenu> ew = new QueryWrapper<SysSettingMenu>();

        return this.list(ew.eq("sys_id",sysId));
    }
}
