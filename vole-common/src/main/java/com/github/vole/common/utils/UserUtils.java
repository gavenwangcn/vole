package com.github.vole.common.utils;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.github.vole.common.constants.CommonConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.List;

/**
 * 用户相关工具类
 */
@Slf4j
public class UserUtils {
    private static final ThreadLocal<String> THREAD_LOCAL_USER = new TransmittableThreadLocal<>();
    private static final String KEY_USER = "user";

    /**
     * 根据请求heard中的token获取用户角色
     *
     * @param httpServletRequest request
     * @return 角色名
     */
    public static List<String> getRole(HttpServletRequest httpServletRequest) {
        String token = getToken(httpServletRequest);
        String key = Base64.getEncoder().encodeToString(CommonConstant.SIGN_KEY.getBytes());
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        List<String> roleNames = (List<String>) claims.get("authorities");
        return roleNames;
    }

    /**
     * 根据header中的token获取用户ID
     *
     * @param httpServletRequest
     * @return 用户ID
     */
    public static Integer getUserId(HttpServletRequest httpServletRequest) {
        String token = getToken(httpServletRequest);
        String key = Base64.getEncoder().encodeToString(CommonConstant.SIGN_KEY.getBytes());
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        Integer userId = (Integer) claims.get("userId");
        return userId;
    }

    /**
     * 获取请求中token
     *
     * @param httpServletRequest request
     * @return cookie
     */
    public static String getToken(HttpServletRequest httpServletRequest) {
        String authorization = httpServletRequest.getHeader(CommonConstant.REQ_HEADER);
        return StringUtils.substringAfter(authorization, CommonConstant.TOKEN_SPLIT);
    }

    /**
     * 设置用户信息
     *
     * @param username 用户名
     */
    public static void setUser(String username) {
        THREAD_LOCAL_USER.set(username);
        MDC.put(KEY_USER, username);
    }

    /**
     * 从threadlocal 获取用户名
     *
     * @return 用户名
     */

    public static String getUser() {
        return THREAD_LOCAL_USER.get();
    }

    public static void clearAllUserInfo() {
        THREAD_LOCAL_USER.remove();
        MDC.remove(KEY_USER);
    }
}
