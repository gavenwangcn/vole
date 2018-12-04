package com.github.vole.auth.controller;

import com.github.vole.common.constants.SecurityConstants;
import com.github.vole.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    @Autowired
    @Qualifier("consumerTokenServices")
    private ConsumerTokenServices consumerTokenServices;

    /**
     * 认证页面
     *
     * @return ModelAndView
     */
    @GetMapping("/require")
    public ModelAndView require() {
        return new ModelAndView("ftl/login");
    }

    /**
     * 用户信息校验
     *
     * @param authentication 信息
     * @return 用户信息
     */
    @RequestMapping("/user")
    public Object user(Authentication authentication) {
        if (authentication != null) {
            return authentication.getPrincipal();
        }
        return null;
    }

    /**
     * 清除Redis中 accesstoken refreshtoken
     *
     * @param accesstoken accesstoken
     * @return true/false
     */
    @PostMapping("/removeToken")
    @CacheEvict(value = SecurityConstants.TOKEN_USER_DETAIL, key = "#accesstoken")
    public R<Boolean> removeToken(String accesstoken) {
        return new R<>(consumerTokenServices.revokeToken(accesstoken));
    }
}