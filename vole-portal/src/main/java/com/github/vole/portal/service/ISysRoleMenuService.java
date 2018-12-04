package com.github.vole.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.vole.portal.model.entity.SysRoleMenu;

import java.util.List;


/**
 *
 * SysRoleMenu 表数据服务层接口
 *
 */
public interface ISysRoleMenuService extends IService<SysRoleMenu> {
	
	/**
	 * 角色授权
	 */
	void addAuth(String roleId, String[] menuIds);
	
	/**
	 * 获取指定角色的权限
	 */
	List<SysRoleMenu> selectByRole(String roleId);

}