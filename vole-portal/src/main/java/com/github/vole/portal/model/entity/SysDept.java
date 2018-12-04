package com.github.vole.portal.model.entity;

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
 * 部门表
 * </p>
 */
@Data
@TableName("sys_dept")
public class SysDept extends Model<SysDept> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value="dept_id",type = IdType.AUTO)
	private Integer id;
    /**
     * 部门名称
     */
	@TableField("dept_name")
	private String deptName;
    /**
     * 描述
     */
	@TableField("dept_desc")
	private String deptDesc;

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
