package com.github.vole.passport.server.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/passport")
public class PassportController {

    /**
     * 认证页面
     *
     * @return ModelAndView
     */
    @GetMapping("/login")
    public ModelAndView login(HttpServletRequest request) {
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




}