package com.github.vole.portal.controller.admin;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.vole.common.utils.R;
import com.github.vole.portal.controller.BaseController;
import com.github.vole.portal.model.entity.SysRole;
import com.github.vole.portal.model.entity.SysUser;
import com.github.vole.portal.model.entity.SysUserRole;
import com.github.vole.portal.service.ISysDeptService;
import com.github.vole.portal.service.ISysRoleService;
import com.github.vole.portal.service.ISysUserRoleService;
import com.github.vole.portal.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.google.common.collect.Lists;

/**
 * 用户控制器
 */
@Controller
@RequestMapping("/admin/user")
public class UserController extends BaseController {

	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private ISysRoleService sysRoleService;
	@Autowired
	private ISysUserRoleService sysUserRoleService;
	@Autowired
	private ISysDeptService sysDeptService;
	
	/**
	 * 分页查询用户
	 */
    @RequestMapping("/list/{pageNumber}")
    public  String list(@PathVariable Integer pageNumber,@RequestParam(defaultValue="15") Integer pageSize,String search,Model model){
		if(StringUtils.isNotBlank(search)){
			model.addAttribute("search", search);
		}
    	Page<Map<Object, Object>> page = getPage(pageNumber,pageSize);
    	Page<Map<Object, Object>> pageData = sysUserService.selectUserPage(page, search);
    	model.addAttribute("pageData", pageData);
    	return "ftl/admin/user/list";
    } 
    /**
     * 新增用户
     */
    @RequestMapping("/add")
    public  String add(Model model){
    	model.addAttribute("roleList", sysRoleService.list(null));
    	model.addAttribute("deptList", sysDeptService.list(null));
		return "ftl/admin/user/add";
    } 
    
    /**
     * 执行新增
     */
    @RequestMapping("/doAdd")
    @ResponseBody
    public R<Boolean> doAdd(SysUser user, @RequestParam(value="roleId[]",required=false) String[] roleIds){
		sysUserService.insertUser(user,roleIds);
		return R.rest(true);
    }
    /**
     * 删除用户
     */
    @RequestMapping("/delete")  
    @ResponseBody
    public  R<Boolean> delete(String id){
    	sysUserService.delete(id);
    	return R.rest(true);
    }  
    
	/**
	 * 编辑用户
	 */
    @RequestMapping("/edit/{id}")  
    public  String edit(@PathVariable String id,Model model){
    	SysUser sysUser = sysUserService.getById(id);
    	
    	List<SysRole> sysRoles = sysRoleService.list(null);
    	QueryWrapper<SysUserRole> ew = new QueryWrapper<SysUserRole>();
    	ew.eq("user_id ", id);
    	List<SysUserRole> mySysUserRoles = sysUserRoleService.list(ew);
    	List<String> myRolds = Lists.transform(mySysUserRoles, input -> input.getRoleId().toString());
    	
    	model.addAttribute("sysUser",sysUser);
    	model.addAttribute("sysRoles",sysRoles);
    	model.addAttribute("myRolds",myRolds);
    	model.addAttribute("deptList", sysDeptService.list(null));
    	return "ftl/admin/user/edit";
    } 
    /**
     * 执行编辑
     */
    @RequestMapping("/doEdit")
    @ResponseBody
    public  R<Boolean> doEdit(SysUser sysUser,@RequestParam(value="roleId[]",required=false) String[] roleId,Model model){
    	sysUserService.updateUser(sysUser,roleId);
    	return R.rest(true);
    } 
    
    /**
     * 验证用户名是否已存在
     */
    @RequestMapping("/checkName")  
    @ResponseBody
    public R<Boolean> checkName(String loginname){
    	List<SysUser> list = sysUserService.list(new QueryWrapper<SysUser>().eq("loginname", loginname));
		R r = new R<Boolean>(true);
    	if(list.size() > 0){
			 r.setData(false);
			 r.setCode(R.FAIL);
    		 r.setMsg("登陆已存在");
    	}
    	return r;
    }
    
}
