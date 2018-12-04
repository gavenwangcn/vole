package com.github.vole.gateway.filter;

import com.alibaba.fastjson.JSONObject;

import com.github.vole.common.bean.config.FilterIgnorePropertiesConfig;
import com.github.vole.common.constants.SecurityConstants;
import com.github.vole.common.utils.AuthUtils;
import com.github.vole.common.utils.R;
import com.github.vole.gateway.exception.ValidateCodeException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

/**
 * <p>
 * security.validate.code.enabled 默认 为false，开启需要设置为true
 */
@Slf4j
@RefreshScope
@Configuration("validateCodeFilter")
@ConditionalOnProperty(value = "security.validate.code", havingValue = "true")
public class ValidateCodeFilter extends ZuulFilter {
    private static final String EXPIRED_CAPTCHA_ERROR = "验证码已过期，请重新获取";

    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_ERROR_FILTER_ORDER + 1;
    }

    /**
     * 是否校验验证码
     * 1. 判断验证码开关是否开启
     * 2. 判断请求是否登录请求
     * 3. 判断终端是否支持
     *
     * @return true/false
     */
    @Override
    public boolean shouldFilter() {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();

        if (!StringUtils.containsIgnoreCase(request.getRequestURI(),
                SecurityConstants.OAUTH_TOKEN_URL)||!StringUtils.containsIgnoreCase(request.getRequestURI(), SecurityConstants.MOBILE_TOKEN_URL)) {
            return false;
        }

        try {
            String[] clientInfos = AuthUtils.extractAndDecodeHeader(request);
            if (CollectionUtils.containsAny(filterIgnorePropertiesConfig.getClients(), Arrays.asList(clientInfos))) {
                return false;
            }
        } catch (IOException e) {
            log.error("解析终端信息失败", e);
        }

        return true;
    }

    @Override
    public Object run() {
        try {
            checkCode(RequestContext.getCurrentContext().getRequest());
        } catch (ValidateCodeException e) {
            RequestContext ctx = RequestContext.getCurrentContext();
            R<String> result = new R<>(e);
            result.setCode(478);

            ctx.setResponseStatusCode(478);
            ctx.setSendZuulResponse(false);
            ctx.getResponse().setContentType("application/json;charset=UTF-8");
            ctx.setResponseBody(JSONObject.toJSONString(result));
        }
        return null;
    }

    /**
     * 检查code
     *
     * @param httpServletRequest request
     * @throws ValidateCodeException 验证码校验异常
     */
    private void checkCode(HttpServletRequest httpServletRequest) throws ValidateCodeException {
        String code = httpServletRequest.getParameter("code");
        if (StringUtils.isBlank(code)) {
            throw new ValidateCodeException("请输入验证码");
        }

        String randomStr = httpServletRequest.getParameter("randomStr");
        if (StringUtils.isBlank(randomStr)) {
            randomStr = httpServletRequest.getParameter("mobile");
        }

        String key = SecurityConstants.DEFAULT_CODE_KEY + randomStr;
        if (!redisTemplate.hasKey(key)) {
            throw new ValidateCodeException(EXPIRED_CAPTCHA_ERROR);
        }

        Object codeObj = redisTemplate.opsForValue().get(key);

        if (codeObj == null) {
            throw new ValidateCodeException(EXPIRED_CAPTCHA_ERROR);
        }

        String saveCode = codeObj.toString();
        if (StringUtils.isBlank(saveCode)) {
            redisTemplate.delete(key);
            throw new ValidateCodeException(EXPIRED_CAPTCHA_ERROR);
        }

        if (!StringUtils.equals(saveCode, code)) {
            redisTemplate.delete(key);
            throw new ValidateCodeException("验证码错误，请重新输入");
        }

        redisTemplate.delete(key);
    }
}
