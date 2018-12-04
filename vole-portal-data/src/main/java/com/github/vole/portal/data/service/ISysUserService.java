package com.github.vole.portal.data.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.vole.portal.data.model.entity.SysUser;
import com.github.vole.portal.data.model.vo.SysUserVO;

import java.util.Map;


/**
 *
 * SysUser 表数据服务层接口
 *
 */
public interface ISysUserService extends IService<SysUser> {

	/**
	 * 根据登陆名查询用户角色信息
	 *
	 * @param loginname 用户名
	 * @return sysUserVo
	 */
	SysUserVO findUserByloginname(String loginname);

	/**
	 * 根据用户名查询用户角色信息
	 *
	 * @param username 用户名
	 * @return sysUserVo
	 */
	SysUserVO findUserByUsername(String username);

	/**
	 * 通过手机号查询用户信息
	 * @param mobile 手机号
	 * @return 用户信息
	 */
	SysUserVO findUserByMobile(String mobile);

}