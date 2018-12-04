package com.github.vole.portal.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.vole.portal.data.model.entity.SysSetting;

import java.util.List;


/**
 *
 * SysSetting 表数据服务层接口
 *
 */
public interface ISysSettingService extends IService<SysSetting> {

	SysSetting findSysByGlobalKey(String sysGlobalKey);


}