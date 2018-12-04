package com.github.vole.auth.component.mobile;

import com.github.vole.auth.model.vo.MemberVO;
import com.github.vole.auth.fegin.MemberService;
import com.github.vole.auth.util.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 手机号登录校验逻辑
 */
public class MobileAuthenticationProvider implements AuthenticationProvider {
    private MemberService memberService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobileAuthenticationToken mobileAuthenticationToken = (MobileAuthenticationToken) authentication;
        MemberVO userVo = memberService.findMemberByMobile((String) mobileAuthenticationToken.getPrincipal());

        if (userVo == null) {
            throw new UsernameNotFoundException("手机号不存在:" + mobileAuthenticationToken.getPrincipal());
        }

        UserDetailsImpl userDetails = buildUserDeatils(userVo);

        MobileAuthenticationToken authenticationToken = new MobileAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationToken.setDetails(mobileAuthenticationToken.getDetails());
        return authenticationToken;
    }

    private UserDetailsImpl buildUserDeatils(MemberVO userVo) {
        return new UserDetailsImpl(userVo);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MobileAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public MemberService getMemberService() {
        return memberService;
    }

    public void setMemberService(MemberService userService) {
        this.memberService = userService;
    }
}
