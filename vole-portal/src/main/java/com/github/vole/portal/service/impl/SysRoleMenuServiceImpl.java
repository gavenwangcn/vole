package com.github.vole.portal.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.vole.portal.model.entity.SysRoleMenu;
import com.github.vole.portal.mapper.SysMenuMapper;
import com.github.vole.portal.mapper.SysRoleMenuMapper;
import com.github.vole.portal.service.ISysRoleMenuService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 *
 * SysRoleMenu 表数据服务层接口实现类
 *
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {
	
	@Resource
	private SysMenuMapper sysMenuMapper;
	
	@Override
	public void addAuth(String roleId, String[] menuIds) {

		/**
		 * 删除原有权限
		 */
		this.remove(new QueryWrapper<SysRoleMenu>().eq("role_id",roleId));
		/**
		 * 重新授权
		 */
		if(ArrayUtils.isNotEmpty(menuIds)){
			for(String menuId : menuIds){
				SysRoleMenu sysRoleMenu2 = new SysRoleMenu();
				sysRoleMenu2.setRoleId(new Integer(roleId));
				sysRoleMenu2.setMenuId(new Integer(menuId));
				this.save(sysRoleMenu2);
			}
		}
	}

	@Override
	public List<SysRoleMenu> selectByRole(String roleId) {
		QueryWrapper<SysRoleMenu> ew = new QueryWrapper<SysRoleMenu>();
		return this.list(ew.eq("role_id",roleId));
		
	}

}