package com.github.vole.portal.model.vo;

import com.github.vole.portal.model.entity.SysMenu;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 菜单树+是否有权限表示
 */
@Data
public class TreeMenuAllowAccess implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 菜单
	 */
	private SysMenu sysMenu;
	/**
	 * 是否允许访问
	 */
	private Integer allowAccess = 0;
	/**
	 * 子菜单
	 */
	private List<TreeMenuAllowAccess> children = new ArrayList<TreeMenuAllowAccess>();
	
}
