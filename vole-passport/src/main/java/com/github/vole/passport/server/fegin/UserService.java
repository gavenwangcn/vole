package com.github.vole.passport.server.fegin;

import com.github.vole.passport.server.entity.vo.SysUserVO;
import com.github.vole.passport.server.service.fallback.UserServiceFallbackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "vole-portal-data", fallback = UserServiceFallbackImpl.class)
public interface UserService {
    /**
     * 通过用户名查询用户、角色信息
     *
     * @param loginname 用户名
     * @return UserVo
     */
    @GetMapping("/user/findUserByLoginname/{loginname}")
    SysUserVO findUserByUsername(@PathVariable("loginname") String loginname);

}
