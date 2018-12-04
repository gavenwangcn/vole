package com.github.vole.common.bean.resolver;

import com.github.vole.common.constants.SecurityConstants;
import com.github.vole.common.vo.RoleVO;
import com.github.vole.common.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Token转化MemberVo
 */
@Slf4j
@Configuration
public class TokenArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 1. 入参筛选
     *
     * @param methodParameter 参数集合
     * @return 格式化后的参数
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(MemberVO.class);
    }

    /**
     * @param methodParameter       入参集合
     * @param modelAndViewContainer vo 和 view
     * @param nativeWebRequest      web相关
     * @param webDataBinderFactory  入参解析
     * @return 包装对象
     * @throws Exception exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        String username = request.getHeader(SecurityConstants.USER_HEADER);
        String roles = request.getHeader(SecurityConstants.ROLE_HEADER);
        if (StringUtils.isBlank(username) || StringUtils.isBlank(roles)) {
            log.warn("resolveArgument error username or role is empty");
            return null;
        } else {
            log.info("resolveArgument username :{} roles:{}", username, roles);
        }
        MemberVO memberVO = new MemberVO();
        memberVO.setMembername(username);
        List<RoleVO> sysRoleVoList = new ArrayList<>();
        Arrays.stream(roles.split(",")).forEach(role -> {
            RoleVO sysRoleVo = new RoleVO();
            sysRoleVo.setRoleName(role);
            sysRoleVoList.add(sysRoleVo);
        });
        memberVO.setRoleVoList(sysRoleVoList);
        return memberVO;
    }

}
