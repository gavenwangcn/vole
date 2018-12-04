package com.github.vole.portal.model.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;


/**
 * <p>
 * 用户角色关联表
 * </p>
 */
@Data
@TableName("sys_user_role")
public class SysUserRole extends Model<SysUserRole> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id",type=IdType.AUTO)
	private Integer id;
    /**
     * 用户主键
     */
	@TableField("user_id")
	private Integer userId;
    /**
     * 角色主键
     */
	@TableField("role_id")
	private Integer roleId;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
