package com.github.vole.mps.controller.rest;

import com.github.vole.mps.model.vo.PermissionVO;
import com.github.vole.mps.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/permission")
public class RestPermissionContoller {

    @Autowired
    private PermissionService permissionService;


    /**
     * 通过角色查询权限信息
     *
     * @param role 用户名
     * @return List<PermissionVO> 对象
     */
    @GetMapping("/findPermissionByRole/{role}")
    public List<PermissionVO> findPermissionByRole(@PathVariable String role) {
        return permissionService.findPermissionByRoleName(role);

    }

}
