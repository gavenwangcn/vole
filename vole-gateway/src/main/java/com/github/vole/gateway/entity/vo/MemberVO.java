package com.github.vole.gateway.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
public class MemberVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long memberId;
    /**
     * 会员名
     */
    private String membername;
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
    private List<RoleVO> roleVoList;
}
