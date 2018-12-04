package com.github.vole.passport.common.handler;

import com.github.vole.passport.common.contants.PassportConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClientLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    private static String targetParamName;

    public void setTargetParamName(String targetParamName) {
        this.targetParamName = targetParamName;
    }

    public ClientLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        String target    = request.getParameter(getTargetParamName());
        String currentUrl = request.getRequestURL().toString();
        if(!StringUtils.isEmpty(target)){
            currentUrl=null;
        }
        String loginFormUrl = this.getLoginFormUrl();
        //处理token回跳
        String queryString =request.getQueryString();
        return buildRequestUri(loginFormUrl,queryString,currentUrl);
    }

    private static String buildRequestUri(String requestURI, String queryString,String currentUrl) {
        StringBuilder url = new StringBuilder(requestURI);
        if (!StringUtils.isEmpty(queryString)) {
            url.append("?").append(queryString);
            if(!StringUtils.isEmpty(currentUrl)) {
              url.append("&").append(getTargetParamName()).append("=").append(currentUrl);
            }
        }else{
            if(!StringUtils.isEmpty(currentUrl)) {
                url.append("?").append(getTargetParamName()).append("=").append(currentUrl);
            }
        }
        return url.toString();
    }

    private static String getTargetParamName() {
        return !StringUtils.isEmpty(targetParamName) ? targetParamName : PassportConstants.DEFAULT_TARGET_PARAM;
    }

}
