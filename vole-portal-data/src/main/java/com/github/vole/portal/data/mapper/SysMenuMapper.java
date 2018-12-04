package com.github.vole.portal.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.vole.portal.data.model.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 *
 * SysMenu 表数据库控制层接口
 *
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

	List findMenuByRoleName(@Param("role") String role);

}