package com.github.vole.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.vole.portal.model.entity.SysRoleMenu;

import java.util.List;

/**
 *
 * SysRoleMenu 表数据库控制层接口
 *
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

	/**
	 * 根据用户Id获取用户所在角色的权限
	 */
	 List<String> selectRoleMenuIdsByUserId(String uid);
	
}