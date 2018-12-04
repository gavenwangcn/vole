package com.github.vole.portal.data.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysSettingMenuVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;
    /**
     * 角色主键
     */
    private Integer sysId;
    /**
     * 菜单主键
     */
    private Integer menuId;
}
