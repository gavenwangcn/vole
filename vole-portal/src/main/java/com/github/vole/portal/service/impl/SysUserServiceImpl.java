package com.github.vole.portal.service.impl;

import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.vole.portal.model.entity.SysUser;
import com.github.vole.portal.model.entity.SysUserRole;
import com.github.vole.portal.mapper.SysUserMapper;
import com.github.vole.portal.mapper.SysUserRoleMapper;
import com.github.vole.portal.service.ISysUserService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 *
 * SysUser 表数据服务层接口实现类
 *
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

	@Resource
	private SysUserMapper userMapper;

	@Resource
	private SysUserRoleMapper userRoleMapper;
	
	@Override
	public void insertUser(SysUser user, String[] roleIds) {
		user.setCreateTime(new Date());
    	user.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getPassword()));
		//保存用户
    	userMapper.insert(user);
		//绑定角色
		if(ArrayUtils.isNotEmpty(roleIds)){
			for(String rid : roleIds){
				SysUserRole sysUserRole = new SysUserRole();
				sysUserRole.setUserId(user.getId());
				sysUserRole.setRoleId(new Integer(rid));
				userRoleMapper.insert(sysUserRole);
			}
		}
		
	}

	@Override
	public void updateUser(SysUser sysUser, String[] roleIds) {
		sysUser.setPassword(null);
		//更新用户
		userMapper.updateById(sysUser);
		//删除已有权限
		userRoleMapper.delete(new QueryWrapper<SysUserRole>().eq("user_id",sysUser.getId()));
		//重新绑定角色
		if(ArrayUtils.isNotEmpty(roleIds)){
			for(String rid : roleIds){
				SysUserRole sysUserRole = new SysUserRole();
				sysUserRole.setUserId(sysUser.getId());
				sysUserRole.setRoleId(new Integer(rid));
				userRoleMapper.insert(sysUserRole);
			}
		}
	}

	@Override
	public Page<Map<Object, Object>> selectUserPage(Page<Map<Object, Object>> page, String search) {
		page.setRecords(userMapper.selectUserList(page, search));
		return page;
	}

	@Override
	public void delete(String id) {
		this.removeById(id);
		userRoleMapper.delete(new QueryWrapper<SysUserRole>().eq("user_id",id));
	}


}