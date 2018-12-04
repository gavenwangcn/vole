package com.github.vole.mps.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.vole.mps.model.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 通过角色名查询菜单
     *
     * @param role 角色名称
     * @return 菜单列表
     */
    List findPermissionByRoleName(@Param("role") String role);
}