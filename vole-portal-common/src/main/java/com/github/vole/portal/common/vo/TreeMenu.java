package com.github.vole.portal.common.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单树
 */
@Data
public class TreeMenu implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * 菜单
	 */
	private SysMenuVO sysMenu;
	/**
	 * 子菜单
	 */
	private List<TreeMenu> children = new ArrayList<TreeMenu>();
	
}
