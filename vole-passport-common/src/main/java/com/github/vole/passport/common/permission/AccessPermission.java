package com.github.vole.passport.common.permission;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface AccessPermission {

    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
