package com.github.vole.portal.service.impl;

import java.util.Date;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.vole.portal.model.entity.SysLog;
import com.github.vole.portal.mapper.SysLogMapper;
import com.github.vole.portal.service.ISysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 *
 * SysLog 表数据服务层接口实现类
 *
 */
@Slf4j
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {


	@Override
	public void insertLog(String title, String uname, String url, String parms) {
		SysLog sysLog  =new SysLog();
		sysLog.setCreateTime(new Date());
		sysLog.setTitle(title);
		sysLog.setUserName(uname);
		sysLog.setUrl(url);
		sysLog.setParams(parms);
		super.save(sysLog);
		log.debug("记录日志:"+sysLog.toString());
	}


}