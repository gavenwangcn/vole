

package com.github.vole.gateway.feign.fallback;


import com.github.vole.gateway.entity.vo.PermissionVO;
import com.github.vole.gateway.feign.RemotePermissionService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


/**
 * why add @Service when i up version ?
 * https://github.com/spring-cloud/spring-cloud-netflix/issues/762
 */
@Slf4j
@Service
public class RemotePermissionServiceFallbackImpl implements RemotePermissionService {
    @Override
    public Set<PermissionVO> findPermissionByRole(String role) {
        log.error("调用{}异常{}","findMenuByRole",role);
        return new HashSet<PermissionVO>();
    }
}
