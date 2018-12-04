package com.github.vole.mps.listener;

import com.github.vole.common.constants.MqQueueConstant;
import com.github.vole.mps.model.entity.SysLog;
import com.github.vole.mps.model.vo.LogVO;
import com.github.vole.mps.service.SysLogService;
import org.slf4j.MDC;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
//@RabbitListener(queues = MqQueueConstant.LOG_QUEUE)
public class LogReceiveListener {
    private static final String KEY_USER = "user";

    @Resource
    private SysLogService sysLogService;

//    @RabbitHandler
    public void receive(LogVO logVo) {
        SysLog sysLog = logVo.getSysLog();
        MDC.put(KEY_USER, logVo.getUsername());
        sysLog.setCreateBy(logVo.getUsername());
        sysLogService.save(sysLog);
        MDC.remove(KEY_USER);
    }
}
