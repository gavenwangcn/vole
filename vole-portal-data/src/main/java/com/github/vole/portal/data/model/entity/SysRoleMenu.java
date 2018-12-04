package com.github.vole.portal.data.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;


/**
 * <p>
 * 角色菜单关联表
 * </p>
 */
@Data
@TableName("sys_role_menu")
public class SysRoleMenu extends Model<SysRoleMenu> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value="id",type=IdType.AUTO)
	private Integer id;
    /**
     * 角色主键
     */
	@TableField("role_id")
	private Integer roleId;
    /**
     * 菜单主键
     */
	@TableField("menu_id")
	private Integer menuId;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
