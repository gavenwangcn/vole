package com.github.vole.portal.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysMenuVO implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 父级菜单ID
     */
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
    private Integer delFlag;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}
