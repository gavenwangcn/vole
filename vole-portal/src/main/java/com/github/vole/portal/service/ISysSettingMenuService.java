package com.github.vole.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.vole.portal.model.entity.SysSettingMenu;

import java.util.List;


/**
 *
 * SysSettingMenu 表数据服务层接口
 *
 */
public interface ISysSettingMenuService extends IService<SysSettingMenu> {
	
	/**
	 * 角色授权
	 */
	void addAuth(String sysId, String[] menuIds);
	
	/**
	 * 获取指定角色的权限
	 */
	List<SysSettingMenu> selectBySetting(String sysId);

}