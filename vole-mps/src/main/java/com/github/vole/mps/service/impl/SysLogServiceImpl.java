package com.github.vole.mps.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.vole.common.constants.CommonConstant;
import com.github.vole.common.validate.Assert;
import com.github.vole.mps.mapper.SysLogMapper;
import com.github.vole.mps.model.entity.SysLog;
import com.github.vole.mps.service.SysLogService;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Override
    public Boolean updateByLogId(Long id) {
        Assert.isNull(id, "日志ID为空");

        SysLog sysLog = new SysLog();
        sysLog.setId(id);
        sysLog.setDelFlag(CommonConstant.STATUS_DEL);
        sysLog.setUpdateTime(new Date());
        return updateById(sysLog);
    }
}
