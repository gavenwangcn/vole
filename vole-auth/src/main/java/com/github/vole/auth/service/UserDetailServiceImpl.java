package com.github.vole.auth.service;

import com.github.vole.auth.model.vo.MemberVO;
import com.github.vole.auth.fegin.MemberService;
import com.github.vole.auth.util.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("userDetailService")
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    private MemberService memberService;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberVO memberVo = memberService.findMemberByMembername(username);
        if (memberVo != null) {
            return new UserDetailsImpl(memberVo);
        }
        throw new UsernameNotFoundException("error username");
    }
}
