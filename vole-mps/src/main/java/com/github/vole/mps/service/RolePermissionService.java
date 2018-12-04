package com.github.vole.mps.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.vole.mps.model.entity.RolePermission;

/**
 * <p>
 * 角色权限表 服务类
 * </p>
 */
public interface RolePermissionService extends IService<RolePermission> {

    /**
     * 更新角色权限
     *
     *
     * @param roleId  角色
     * @param permissionIds 权限列表
     * @return
     */
    void authPermissions(String roleId, String[] permissionIds);

}
