package com.github.vole.portal.controller.admin;

import com.github.vole.portal.controller.BaseController;
import com.github.vole.portal.model.AnonymousSysUser;
import com.github.vole.portal.model.entity.SysUser;
import com.github.vole.portal.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 用户中心控制器
 */
@Controller
@RequestMapping("/admin/me")
public class MeController extends BaseController {

    @Autowired
    private ISysUserService sysUserService;

    /**
     * 个人信息
     *
     * @param model
     * @return
     */
    @RequestMapping("/info")
    public String info(Model model) {
        Integer userId = getUserId();
        SysUser sysUser ;
        if (userId != null) {
            sysUser = sysUserService.getById(getUserId());
        } else {
            sysUser = new AnonymousSysUser();
        }
        model.addAttribute("sysUser", sysUser);
        return "ftl/admin/me/info";
    }


    /**
     * 修改密码页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/pwd")
    public String pwd(Model model) {
        return "ftl/admin/me/pwd";
    }

    /**
     * 修改密码
     */
    @RequestMapping("/doChangePwd")
    public String doChangePwd(String password, String newpassword, String newpassword2, Model model, RedirectAttributes redirectAttributes) {
        Integer userId = getUserId();
        if(userId==null){
            redirectAttributes.addFlashAttribute("msg", "匿名用户");
            return redirectTo("/admin/me/pwd");
        }
        if (StringUtils.isBlank(password) || StringUtils.isBlank(newpassword) || StringUtils.isBlank(newpassword2)) {
            redirectAttributes.addFlashAttribute("msg", "客户端提交数据不能为空.");
            return redirectTo("/admin/me/pwd");
        }
        SysUser user = sysUserService.getById(userId);
        if (!user.getPassword().equals(PasswordEncoderFactories.
                createDelegatingPasswordEncoder().encode(password))) {
            redirectAttributes.addFlashAttribute("msg", "旧密码输入错误.");
            return redirectTo("/admin/me/pwd");
        }

        if (!newpassword2.equals(newpassword)) {
            redirectAttributes.addFlashAttribute("msg", "两次输入的密码不一致.");
            return redirectTo("/admin/me/pwd");
        }

        user.setPassword(PasswordEncoderFactories.
                createDelegatingPasswordEncoder().encode(newpassword));
        sysUserService.updateById(user);

        redirectAttributes.addFlashAttribute("info", "密码修改成功.");
        return redirectTo("/admin/me/pwd");
    }

    /**
     * 更新用户
     *
     * @param sysUser
     * @param model
     * @return
     */
    @RequestMapping("/updateUser")
    public String updateUser(SysUser sysUser, Model model, RedirectAttributes redirectAttributes) {
        Integer userId = getUserId();
        if(userId==null){
            redirectAttributes.addFlashAttribute("info", "匿名用户");
            return redirectTo("/admin/me/info");
        }
        SysUser user = sysUserService.getById(userId);
        if (StringUtils.isNotBlank(user.getAvatar())) {
            user.setAvatar(sysUser.getAvatar());
        }
        sysUserService.updateById(user);
        model.addAttribute("sysUser", user);
        redirectAttributes.addFlashAttribute("info", "更新成功.");
        return redirectTo("/admin/me/info");
    }
}
