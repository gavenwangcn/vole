package com.github.vole.portal.model.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;


/**
 * <p>
 * 用户表
 * </p>
 *
 */
@Data
@TableName("sys_user")
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID = 1L;
    
    public static final int _0 = 0;
	public static final int _1 = 1;
    
    /**
     * 主键
     */
    @TableId(value = "user_id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 登陆名
	 */
	private String loginname;
	/**
	 * 用户名
	 */
	private String username;

	private String password;
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
	/**
	 * 0-正常，1-删除
	 */
	@TableField("del_flag")
	private Integer delFlag;
	/**
	 * 简介
	 */
	private String phone;
	/**
	 * 头像
	 */
	private String avatar;
	/**
	 * 部门id
	 */
	@TableField("dept_id")
	private Integer deptId;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
