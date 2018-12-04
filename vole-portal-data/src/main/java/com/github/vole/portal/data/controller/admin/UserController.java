package com.github.vole.portal.data.controller.admin;

import com.github.vole.portal.data.model.entity.SysUser;
import com.github.vole.portal.data.model.vo.SysUserVO;
import com.github.vole.portal.data.service.ISysUserService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 用户控制器
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ISysUserService sysUserService;


    /**
     * 通过登陆名查询用户及其角色信息
     *
     * @param id 用户名
     * @return UseVo 对象
     */
    @GetMapping(value = "/findUserById/{id}")
    public SysUserVO findUserById(@PathVariable String id) {
        SysUserVO userVO = new SysUserVO();
        SysUser user = sysUserService.getById(id);
        if (user != null) {
            userVO.setUserId(user.getId());
            BeanUtils.copyProperties(user, userVO);
        }
        return userVO;
    }


    /**
     * 通过登陆名查询用户及其角色信息
     *
     * @param loginname 用户名
     * @return UseVo 对象
     */
    @GetMapping(value = "/findUserByLoginname/{loginname}")
    public SysUserVO findUserByLoginname(@PathVariable String loginname) {
        return sysUserService.findUserByloginname(loginname);
    }


    /**
     * 通过用户名查询用户及其角色信息
     *
     * @param username 用户名
     * @return UseVo 对象
     */
    @GetMapping(value = "/findUserByUsername/{username}")
    public SysUserVO findUserByUsername(@PathVariable String username) {
        return sysUserService.findUserByUsername(username);
    }

    /**
     * 通过手机号查询用户及其角色信息
     *
     * @param mobile 手机号
     * @return UseVo 对象
     */
    @RequestMapping(value = "/findUserByMobile/{mobile}", method = RequestMethod.GET)
    public SysUserVO findUserByMobile(@PathVariable String mobile) {
        return sysUserService.findUserByMobile(mobile);
    }

}
