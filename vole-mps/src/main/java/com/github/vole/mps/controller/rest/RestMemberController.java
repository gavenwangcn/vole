package com.github.vole.mps.controller.rest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.vole.common.constants.CommonConstant;
import com.github.vole.common.utils.R;
import com.github.vole.mps.model.dto.MemberDTO;
import com.github.vole.mps.model.dto.MemberInfo;
import com.github.vole.mps.model.entity.Member;
import com.github.vole.mps.model.entity.MemberRole;
import com.github.vole.mps.model.entity.Role;
import com.github.vole.mps.model.vo.MemberVO;
import com.github.vole.mps.model.vo.RoleVO;
import com.github.vole.mps.service.MemberRoleService;
import com.github.vole.mps.service.MemberService;
import com.github.vole.mps.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/rest/member")
public class RestMemberController {
    private static final PasswordEncoder ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRoleService memberRoleService;

    @Autowired
    private RoleService roleService;



    /**
     * 获取当前用户信息（角色、权限）
     * 并且异步初始化用户部门信息
     *
     * @param memberVo 当前用户信息
     * @return 用户名
     */
    @GetMapping("/info")
    public R<MemberInfo> member(MemberVO memberVo) {
        MemberInfo memberInfo = memberService.findMemberInfo(memberVo);
        return new R<>(memberInfo);
    }


    /**
     * 添加用户
     *
     * @param memberDto 用户信息
     * @return success/false
     */
    @PostMapping
    public R<Boolean> member(@RequestBody MemberDTO memberDto) {
        Member member = new Member();
        BeanUtils.copyProperties(memberDto, member);
        member.setDelFlag(CommonConstant.STATUS_NORMAL);
        member.setPassword(ENCODER.encode(memberDto.getNewpassword1()));
        memberService.save(member);

        memberDto.getRole().forEach(roleId -> {
            MemberRole memberRole = new MemberRole();
            memberRole.setMemberId(member.getMemberId());
            memberRole.setRoleId(roleId);
            memberRole.insert();
        });
        return new R<>(Boolean.TRUE);
    }


    /**
     * 通过用户名查询用户及其角色信息
     *
     * @param membername 用户名
     * @return MemberVo 对象
     */
    @GetMapping("/findMemberByMembername/{membername}")
    public MemberVO findUserByUsername(@PathVariable String membername) {
        MemberVO vo = new MemberVO();
        List<RoleVO> roleVOList = new ArrayList<RoleVO>();
        QueryWrapper<Member> qw = new QueryWrapper();
        qw.eq("membername",membername);
        Member m = memberService.getOne(qw);
        if(m!=null){
            BeanUtils.copyProperties(m,vo);
        }
        Long memberId = m.getMemberId();
        QueryWrapper<MemberRole> mRqw = new QueryWrapper();
        mRqw.eq("member_id",memberId);
        List<MemberRole> memberRoleList = memberRoleService.list(mRqw);
        if(!CollectionUtils.isEmpty(memberRoleList)){
            List<Integer> roleIds = new ArrayList<Integer>();
            memberRoleList.forEach(input ->{
                roleIds.add(input.getRoleId());
            });
            QueryWrapper<Role> rqw = new QueryWrapper();
            rqw.in("role_id",roleIds);
            List<Role> roles = roleService.list(rqw);
            roles.forEach(input ->{
                RoleVO rvo = new RoleVO();
                BeanUtils.copyProperties(input,rvo);
                roleVOList.add(rvo);
            });
        }
        vo.setRoleVoList(roleVOList);
        return vo;
    }

    /**
     * 通过手机号查询用户及其角色信息
     *
     * @param mobile 手机号
     * @return MemberVo 对象
     */
    @GetMapping("/findMemberByMobile/{mobile}")
    public MemberVO findMemberByMobile(@PathVariable String mobile)
    {
        MemberVO vo = new MemberVO();
        List<RoleVO> roleVOList = new ArrayList<RoleVO>();
        QueryWrapper<Member> qw = new QueryWrapper();
        qw.eq("mobile",mobile);
        Member m = memberService.getOne(qw);
        if(m!=null){
            BeanUtils.copyProperties(m,vo);
        }
        Long memberId = m.getMemberId();
        QueryWrapper<MemberRole> mRqw = new QueryWrapper();
        mRqw.eq("member_id",memberId);
        List<MemberRole> memberRoleList = memberRoleService.list(mRqw);
        if(!CollectionUtils.isEmpty(memberRoleList)){
            List<Integer> roleIds = new ArrayList<Integer>();
            memberRoleList.forEach(input ->{
                roleIds.add(input.getRoleId());
            });
            QueryWrapper<Role> rqw = new QueryWrapper();
            rqw.in("role_id",roleIds);
            List<Role> roles = roleService.list(rqw);
            roles.forEach(input ->{
                RoleVO rvo = new RoleVO();
                BeanUtils.copyProperties(input,rvo);
                roleVOList.add(rvo);
            });
        }
        vo.setRoleVoList(roleVOList);
        return vo;
    }



}
