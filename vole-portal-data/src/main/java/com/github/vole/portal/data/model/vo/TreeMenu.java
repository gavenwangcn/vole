package com.github.vole.portal.data.model.vo;


import com.github.vole.portal.data.model.entity.SysMenu;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

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
	private SysMenu sysMenu;
	/**
	 * 子菜单
	 */
	private List<TreeMenu> children = new ArrayList<TreeMenu>();
	
}
