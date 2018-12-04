package com.github.vole.common.utils;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description CookieUtil用来操作cookie的存取
 */
public class CookieUtil {
    /**
     * 添加cookie
     * @param name cookie的key
     * @param value cookie的value
     * @param domain domain
     * ＠param  path path
     * @param maxage  最长存活时间 单位为秒
     * @param response
     */
    public static void addCookie(String name ,String value,String domain,
        int maxage,String path, HttpServletResponse response){
        Cookie cookie = new Cookie(name,value);
        if(domain!=null){
            cookie.setDomain(domain);
        }
        cookie.setMaxAge(maxage);
        cookie.setPath(path);
        response.addCookie(cookie);
    }

    /**
     * 往根下面存一个cookie
     * * @param name cookie的key
     * @param value cookie的value
     * @param domain domain
     * @param maxage  最长存活时间 单位为秒
     * @param response
     */
    public static void addCookie(String name ,String value,String domain,
        int maxage, HttpServletResponse response){
        addCookie(name, value,domain, maxage, "/" , response);
    }

    /**
     * 从cookie值返回cookie值，如果没有返回 null
     * @param request
     * @param name
     * @return cookie的值
     */
    public static String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals(name)) {
                return cookies[i].getValue();
            }
        }
        return null;
    }

    public static void removeCookie(String name, String domain, HttpServletRequest request, HttpServletResponse response) {
        String cookieVal = getCookie(request,name);
        if(cookieVal!=null){
            CookieUtil.addCookie(name, null, domain, 0, response);
        }
    }

}