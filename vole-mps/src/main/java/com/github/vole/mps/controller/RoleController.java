package com.github.vole.mps.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.vole.common.constants.CommonConstant;
import com.github.vole.common.utils.R;
import com.github.vole.portal.common.controller.AbstractController;
import com.github.vole.mps.model.entity.Permission;
import com.github.vole.mps.model.entity.Role;
import com.github.vole.mps.model.entity.RolePermission;
import com.github.vole.mps.service.PermissionService;
import com.github.vole.mps.service.RolePermissionService;
import com.github.vole.mps.service.RoleService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/mps/role")
public class RoleController extends AbstractController<RoleService,Role> {

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private PermissionService permissionService;


    /**
     * 权限
     */
    @RequestMapping("/auth/{id}")
    public  String auth(@PathVariable String id,Model model){

        Role sysRole = baseService.getById(id);

        if(sysRole == null){
            throw new RuntimeException("该角色不存在");
        }

        List<RolePermission> rolePermissions = rolePermissionService.list(new QueryWrapper<RolePermission>().eq("role_id", id));
        List<String> permissionIds = Lists.transform(rolePermissions, input -> input.getPermissionId().toString());
        List<Permission> permissions = permissionService.list(new QueryWrapper<Permission>().orderByAsc("permission_id"));
        model.addAttribute("sysRole", sysRole);
        model.addAttribute("permissionIds", permissionIds);
        model.addAttribute("permissions", permissions);

        return "ftl/mps/role/auth";
    }

    /**
     * 权限
     */
    @RequestMapping("/doAuth")
    @ResponseBody
    public  R<Boolean> doAuth(String roleId,@RequestParam(value="permissionId[]",required=false) String[] permissionIds){
        rolePermissionService.authPermissions(roleId,permissionIds);
        R<Boolean> r = R.rest(true);
        r.setMsg("OK,授权成功,1分钟后生效  ~");
        return r;
    }


    @Override
    protected String getTemplatePath() {
        return "ftl/mps/role/";
    }

    @Override
    protected QueryWrapper<Role> getSearchQW(String search, Model model) {
        QueryWrapper<Role> qw = new QueryWrapper<Role>();
        if(StringUtils.isNotBlank(search)){
            qw.like("role_name",search);
            model.addAttribute("search",search);
        }
        return qw;
    }
}
