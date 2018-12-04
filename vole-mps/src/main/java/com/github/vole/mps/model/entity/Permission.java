package com.github.vole.mps.model.entity;

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
 * 权限表
 * </p>
 */
@Data
@TableName("permission")
public class Permission extends Model<Permission> {

    private static final long serialVersionUID = 1L;

    /**
     * 权限ID
     */
    @TableId(value = "permission_id",type = IdType.AUTO)
	private Integer permissionId;
    /**
     * 权限标识
     */
	private String permission;
	/**
	 * 请求链接
	 */
	private String url;
	/**
	 * 请求方法
	 */
	private String method;
    /**
     * 权限描述
     */
	private String description;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 更新时间
     */
	@TableField("update_time")
	private Date updateTime;


	@Override
	protected Serializable pkVal() {
		return this.permissionId;
	}

	@Override
	public String toString() {
		return "Permission{" +
			", menuId=" + permissionId +
			", permission=" + permission +
			", description=" + description +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			"}";
	}
}
