package com.github.vole.portal.data.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * <p>
 * 系统设置表
 * </p>
 *
 */
@Data
@TableName("sys_setting")
public class SysSetting extends Model<SysSetting> {

    private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value="sys_id",type = IdType.AUTO)
	private String id;
	/**
	 * 名称
	 */
	@TableField("sys_name")
	private String sysName;
	/**
	 * 值
	 */
	@TableField("sys_sub_name")
	private String sysSubName;
	/**
	 * 值
	 */
	@TableField("sys_global_key")
	private String sysGlobalKey;
	/**
	 * 排序
	 */
	private Integer sort;

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
