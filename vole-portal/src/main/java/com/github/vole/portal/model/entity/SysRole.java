package com.github.vole.portal.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;


import java.io.Serializable;


/**
 * <p>
 * 角色表
 * </p>
 */
@Data
@TableName("sys_role")
public class SysRole extends Model<SysRole> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "role_id",type = IdType.AUTO)
	private Integer id;
    /**
     * 角色名称
     */
	@TableField("role_name")
	private String roleName;
	/**
	 * 角色编码
	 */
	@TableField("role_code")
	private String roleCode;
    /**
     * 角色描述
     */
	@TableField("role_desc")
	private String roleDesc;
    /**
     * 状态,0-启用,1禁用
     */
	@TableField("del_flag")
	private Integer delFlag;
	/**
	 * 创建时间
	 */
	@TableField("create_time")
	private Date createTime;
	/**
	 * 修改时间
	 */
	@TableField("update_time")
	private Date updateTime;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
