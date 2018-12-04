package com.github.vole.portal.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.vole.portal.data.model.entity.SysUser;
import com.github.vole.portal.data.model.vo.SysUserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 *
 * SysUser 表数据库控制层接口
 *
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

	List<Map<Object, Object>> selectUserList(Page<Map<Object, Object>> page, @Param("search") String search);

	/**
	 * 通过登陆名查询用户信息（含有角色信息）
	 *
	 * @param loginname 用户名
	 * @return SysUserVO
	 */
	SysUserVO selectUserVoByLoginname(String loginname);

	/**
	 * 通过用户名查询用户信息（含有角色信息）
	 *
	 * @param username 用户名
	 * @return SysUserVO
	 */
	SysUserVO selectUserVoByUsername(String username);

	/**
	 * 通过手机号查询用户信息（含有角色信息）
	 *
	 * @param mobile 用户名
	 * @return SysUserVO
	 */
	SysUserVO selectUserVoByMobile(String mobile);
}