package com.github.vole.portal.common.fegin;

import com.github.vole.portal.common.fegin.fallback.UserServiceFallbackImpl;
import com.github.vole.portal.common.vo.SysUserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "vole-portal-data", fallback = UserServiceFallbackImpl.class)
public interface UserService {

    @GetMapping("/user/findUserById/{id}")
    SysUserVO findUserById(@PathVariable("id") String userId);
}
