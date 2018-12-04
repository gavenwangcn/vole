package com.github.vole.portal.data.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SysUserVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    private Integer userId;
    /**
     * 登陆名
     */
    private String loginname;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 0-正常，1-删除
     */
    private String delFlag;
    /**
     * 简介
     */
    private String phone;
    /**
     * 头像
     */
    private String avatar;

    /**
     * 角色列表
     */
    private List<SysRoleVO> roleVoList;
}
