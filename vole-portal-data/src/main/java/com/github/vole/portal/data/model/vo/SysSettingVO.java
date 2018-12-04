package com.github.vole.portal.data.model.vo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysSettingVO implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 名称
     */
    private String sysName;
    /**
     * 值
     */
    private String sysSubName;
    /**
     * 值
     */
    private String sysGlobalKey;
    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}
