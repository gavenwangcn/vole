package com.github.vole.portal.model.entity;


import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;


/**
 * <p>
 * 日志表
 * </p>
 */
@Data
@TableName("sys_log")
public class SysLog extends Model<SysLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value = "id",type = IdType.AUTO)
	private Integer id;
    /**
     * 用户
     */
	@TableField("user_name")
	private String userName;
    /**
     * 日志
     */
	private String title;
    /**
     * 地址
     */
	private String url;
    /**
     * 参数
     */
	private String params;
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
