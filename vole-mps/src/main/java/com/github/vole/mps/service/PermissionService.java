package com.github.vole.mps.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.vole.mps.model.entity.Permission;
import com.github.vole.mps.model.vo.PermissionVO;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 */
public interface PermissionService extends IService<Permission> {
    /**
     * 通过角色名称查询权限
     *
     * @param role 角色名称
     * @return 权限信息列表
     */
    List<PermissionVO> findPermissionByRoleName(String role);

    /**
     * 删除权限信息
     *
     * @param id   权限ID
     * @return 成功、失败
     */
    Boolean deletePermission(Integer id);

    /**
     * 更新权限信息
     *
     * @param permission 权限信息
     * @return 成功、失败
     */
    Boolean updatePermissionById(Permission permission);
}
