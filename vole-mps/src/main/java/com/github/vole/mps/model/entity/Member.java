package com.github.vole.mps.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class Member extends Model<Member> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "member_id", type = IdType.ID_WORKER)
    private Long memberId;
    /**
     * 用户名
     */
    private String membername;

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
    private String delFlag;

    /**
     * 简介
     */
    private String phone;
    /**
     * 头像
     */
    private String avatar;



    @Override
    protected Serializable pkVal() {
        return this.memberId;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", membername='" + membername + '\'' +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", delFlag='" + delFlag + '\'' +
                ", phone='" + phone + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
