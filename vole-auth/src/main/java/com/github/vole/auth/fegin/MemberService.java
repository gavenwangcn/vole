package com.github.vole.auth.fegin;

import com.github.vole.auth.model.vo.MemberVO;
import com.github.vole.auth.service.fallback.MemberServiceFallbackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "vole-mps", fallback = MemberServiceFallbackImpl.class)
public interface MemberService {
    /**
     * 通过用户名查询用户、角色信息
     *
     * @param membername 用户名
     * @return MemberVO
     */
    @GetMapping("/rest/member/findMemberByMembername/{membername}")
    MemberVO findMemberByMembername(@PathVariable("membername") String membername);

    /**
     * 通过手机号查询用户、角色信息
     *
     * @param mobile 手机号
     * @return MemberVO
     */
    @GetMapping("/member/findMemberByMobile/{mobile}")
    MemberVO findMemberByMobile(@PathVariable("mobile") String mobile);

    /**
     * 根据OpenId查询用户信息
     * @param openId openId
     * @return MemberVO
     */
    @GetMapping("/member/findMemberByOpenId/{openId}")
    MemberVO findMemberByOpenId(@PathVariable("openId") String openId);
}
