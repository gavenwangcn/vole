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
 * 菜单表
 * </p>
 */
@Data
@TableName("sys_menu")
public class SysMenu extends Model<SysMenu> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value="menu_id",type=IdType.AUTO)
	private Integer id;
    /**
     * 菜单名称
     */
	@TableField("menu_name")
	private String menuName;
    /**
     * 父级菜单ID
     */
	@TableField(value="parent_id")
	private Integer parentId;
    /**
     * 连接地址
     */
	private String url;
    /**
     * 图标
     */
	private String icon;
    /**
     * 排序
     */
	private Integer sort;
    /**
     * 深度
     */
	private Integer deep;
    /**
     * 编码
     */
	private String code;
    /**
     * 资源名称
     */
	private String resource;
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
