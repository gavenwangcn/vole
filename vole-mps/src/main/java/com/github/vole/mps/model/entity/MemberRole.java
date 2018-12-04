package com.github.vole.mps.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户角色表
 * </p>
 */
@Data
public class MemberRole extends Model<MemberRole> {

    private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(type  = IdType.ID_WORKER)
    private Long id;

    /**
     * 用户ID
     */
    @TableId(type = IdType.INPUT)
	private Long memberId;
    /**
     * 角色ID
     */
	@TableId(type = IdType.INPUT)
	private Integer roleId;


	@Override
	protected Serializable pkVal() {
		return this.memberId;
	}

	@Override
	public String toString() {

		return "MemberRole{" +
			", userId=" + memberId +
			", roleId=" + roleId +
			"}";
	}
}
