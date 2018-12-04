package com.github.vole.gateway.service.impl;

import com.github.vole.common.constants.CommonConstant;
import com.github.vole.common.constants.MqQueueConstant;
import com.github.vole.common.utils.IOUtils;
import com.github.vole.gateway.entity.SysLog;
import com.github.vole.gateway.entity.vo.LogVO;
import com.github.vole.gateway.service.LogSendService;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.util.HTTPRequestUtils;
//import io.netty.handler.codec.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
//import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * 消息发往消息队列工具类
 */
@Slf4j
@Component
public class LogSendServiceImpl implements LogSendService {
    private static final String SERVICE_ID = "serviceId";
//    @Autowired
//    private AmqpTemplate rabbitTemplate;

    /**
     * 1. 获取 requestContext 中的请求信息
     * 2. 如果返回状态不是OK，则获取返回信息中的错误信息
     * 3. 发送到MQ
     *
     * @param requestContext 上下文对象
     */
    @Override
    public void send(RequestContext requestContext) {
        HttpServletRequest request = requestContext.getRequest();
        String requestUri = request.getRequestURI();
        String method = request.getMethod();
        SysLog sysLog = new SysLog();
        sysLog.setType(CommonConstant.STATUS_NORMAL);
        sysLog.setRemoteAddr(HTTPRequestUtils.getInstance().getClientIP(request));
        sysLog.setRequestUri(requestUri);
        sysLog.setMethod(method);
        sysLog.setUserAgent(request.getHeader("member-agent"));
        sysLog.setParams(request.getParameterMap().toString());
        Long startTime = (Long) requestContext.get("startTime");
        sysLog.setTime(System.currentTimeMillis() - startTime);
        if (requestContext.get(SERVICE_ID) != null) {
            sysLog.setServiceId(requestContext.get(SERVICE_ID).toString());
        }

        //正常发送服务异常解析
        if (requestContext.getResponseStatusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR
                && requestContext.getResponseDataStream() != null) {
            InputStream inputStream = requestContext.getResponseDataStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            InputStream stream1 = null;
            InputStream stream2;
            try {
                byte[] buffer = IOUtils.readBytes(inputStream,true);
                baos.write(buffer);
                baos.flush();
                stream1 = new ByteArrayInputStream(baos.toByteArray());
                stream2 = new ByteArrayInputStream(baos.toByteArray());
                String resp = IOUtils.toString(stream1, Charset.forName(CommonConstant.UTF8),true);
                sysLog.setType(CommonConstant.STATUS_LOCK);
                sysLog.setException(resp);
                requestContext.setResponseDataStream(stream2);
            } catch (IOException e) {
                log.error("响应流解析异常：", e);
                throw new RuntimeException(e);
            } finally {
                IOUtils.close(stream1);
                IOUtils.close(baos);
                IOUtils.close(inputStream);
            }
        }

        //网关内部异常
        Throwable throwable = requestContext.getThrowable();
        if (throwable != null) {
            log.error("网关异常", throwable);
            sysLog.setException(throwable.getMessage());
        }
        //保存发往MQ（只保存授权）
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && StringUtils.isNotBlank(authentication.getName())) {
            LogVO logVo = new LogVO();
            sysLog.setCreateBy(authentication.getName());
            logVo.setSysLog(sysLog);
            logVo.setUsername(authentication.getName());
//            rabbitTemplate.convertAndSend(MqQueueConstant.LOG_QUEUE, logVo);
        }
    }
}
