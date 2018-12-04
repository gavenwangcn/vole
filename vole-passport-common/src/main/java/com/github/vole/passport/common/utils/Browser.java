package com.github.vole.passport.common.utils;

import javax.servlet.http.HttpServletRequest;


/**
 * <p>
 * 验证浏览器基本信息
 * </p>
 */
public class Browser {

  /**
   * <p>
   * 混淆浏览器版本信息，取 MD5 中间部分字符
   * </p>
   *
   * @param request
   * @return
   * @since 获取浏览器客户端信息签名值
   */
  public static String getUserAgent(HttpServletRequest request) {
    String userAgent = request.getHeader("member-agent");
    if (null == userAgent) {
      return null;
    }
    return userAgent.substring(3, 8);
  }

  /**
   * <p>
   * 请求浏览器是否合法 (只校验客户端信息不校验domain)
   * </p>
   *
   * @param request
   * @param userAgent 浏览器客户端信息
   * @return
   */
  public static boolean isLegalUserAgent(HttpServletRequest request, String userAgent) {
    String ua = getUserAgent(request);
    if (null == ua) {
      return false;
    }
    return ua.equals(userAgent);
  }

}
