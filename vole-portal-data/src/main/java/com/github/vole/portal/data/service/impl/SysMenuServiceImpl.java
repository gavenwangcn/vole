package com.github.vole.portal.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.vole.portal.data.mapper.SysMenuMapper;
import com.github.vole.portal.data.mapper.SysRoleMenuMapper;
import com.github.vole.portal.data.model.entity.SysMenu;
import com.github.vole.portal.data.model.vo.SysMenuVO;
import com.github.vole.portal.data.model.vo.TreeMenu;
import com.github.vole.portal.data.service.ISysMenuService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * SysMenu 表数据服务层接口实现类
 *
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {
	/**
	 * 菜单服务
	 */
	@Resource
	private SysMenuMapper sysMenuMapper;
	/**
	 * 角色菜单关系服务
	 */
	@Resource
	private SysRoleMenuMapper sysRoleMenuMapper;

	
	@Override
	public List<TreeMenu> selectTreeMenuByUserId(String uid) {
		/**
		 * 当前用户二级菜单权限
		 */
		List<String> menuIds = sysRoleMenuMapper.selectRoleMenuIdsByUserId(uid);
		return selectTreeMenuByMenuIdsAndPid(menuIds, "0");
	}
	
	@Override
	public List<TreeMenu> selectTreeMenuByMenuIdsAndPid(final List<String> menuIds, String parentId) {
		List<TreeMenu> treeMenus = new ArrayList<TreeMenu>();
		if(menuIds.size() <0){
			return treeMenus;
		}
		QueryWrapper<SysMenu> ew = new QueryWrapper<SysMenu>();
		ew.orderByAsc("sort");
		ew.eq("parent_id", parentId);
		ew.in("menu_id", menuIds);
		List<SysMenu> sysMenus = this.list(ew);
		for(SysMenu sysMenu : sysMenus){
			TreeMenu treeMenu = new TreeMenu();
			treeMenu.setSysMenu(sysMenu);
			if(sysMenu.getDeep() < 2){
				treeMenu.setChildren(selectTreeMenuByMenuIdsAndPid(menuIds,sysMenu.getId().toString()));
			}
			treeMenus.add(treeMenu);
		}
		return treeMenus;
	}


	@Override
	@Cacheable(value = "menu_details", key = "#role  + '_menu'")
	public List<SysMenuVO> findMenusByRoleName(String role) {
		return sysMenuMapper.findMenuByRoleName(role);
	}


}