package com.github.vole.common.bean.aop;

import com.github.vole.common.constants.SecurityConstants;
import com.github.vole.common.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Controller 增强
 */

@Slf4j
@Aspect
@Component
public class ControllerAop {
    @Pointcut("execution(public com.github.vole.common.utils.R *(..))")
    public void pointCutR() {
    }

    /**
     * 拦截器具体实现
     *
     * @param pjp 切点 所有返回对象R
     * @return R  结果包装
     */
    @Around("pointCutR()")
    public Object methodRHandler(ProceedingJoinPoint pjp) throws Throwable {
        return methodHandler(pjp);
    }


    @Pointcut("execution(public com.baomidou.mybatisplus.extension.plugins.pagination.Page *(..))")
    public void pointCutPage() {
    }

    /**
     * 拦截器具体实现
     *
     * @param pjp 切点 所有返回对象Page
     * @return R  结果包装
     */
    @Around("pointCutPage()")
    public Object methodPageHandler(ProceedingJoinPoint pjp) throws Throwable {
        return methodHandler(pjp);
    }

    private Object methodHandler(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String username = request.getHeader(SecurityConstants.USER_HEADER);
        if (StringUtils.isNotBlank(username)) {
            log.info("Controller AOP get username:{}", username);
            UserUtils.setUser(username);
        }

        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        log.info("CLASS_METHOD : " + pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName());
        log.info("ARGS : " + Arrays.toString(pjp.getArgs()));

        Object result;

        result = pjp.proceed();
        log.info(pjp.getSignature() + "use time:" + (System.currentTimeMillis() - startTime));

        if (StringUtils.isNotEmpty(username)) {
            UserUtils.clearAllUserInfo();
        }

        return result;
    }
}
