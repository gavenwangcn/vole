package com.github.vole.mps.service.impl;

import com.alibaba.fastjson.JSONObject;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.vole.common.constants.SecurityConstants;
import com.github.vole.common.utils.R;
import com.github.vole.mps.mapper.MemberMapper;
import com.github.vole.mps.mapper.MemberRoleMapper;
import com.github.vole.mps.model.dto.MemberDTO;
import com.github.vole.mps.model.dto.MemberInfo;
import com.github.vole.mps.model.entity.Member;
import com.github.vole.mps.model.entity.MemberRole;
import com.github.vole.mps.model.vo.MemberVO;
import com.github.vole.mps.model.vo.PermissionVO;
import com.github.vole.mps.model.vo.RoleVO;
import com.github.vole.mps.service.PermissionService;
import com.github.vole.mps.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    private static final PasswordEncoder ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private MemberMapper memberMapper;
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
    @Resource
    private MemberRoleMapper memberRoleMapper;

    @Autowired
    private PermissionService permissionService;

    @Override
    public MemberInfo findMemberInfo(MemberVO memberVO) {
        Member condition = new Member();
        condition.setMembername(memberVO.getMembername());
        Member member = this.getOne(new QueryWrapper<>(condition));

        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setMember(member);
        //设置角色列表
        List<RoleVO> roleList = memberVO.getRoleVoList();
        List<String> roleNames = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(roleList)) {
            for (RoleVO role : roleList) {
                if (!StringUtils.equals(SecurityConstants.BASE_ROLE, role.getRoleName())) {
                    roleNames.add(role.getRoleName());
                }
            }
        }
        String[] roles = roleNames.toArray(new String[roleNames.size()]);
        memberInfo.setRoles(roles);

        //设置权限列表（permission）
        Set<PermissionVO> permissionVoSet = new HashSet<>();
        for (String role : roles) {
            List<PermissionVO> permissionVos = permissionService.findPermissionByRoleName(role);
            permissionVoSet.addAll(permissionVos);
        }
        Set<String> permissions = new HashSet<>();
        for (PermissionVO permissionVo : permissionVoSet) {
            if (StringUtils.isNotEmpty(permissionVo.getPermission())) {
                String permission = permissionVo.getPermission();
                permissions.add(permission);
            }
        }
        memberInfo.setPermissions(permissions.toArray(new String[permissions.size()]));
        return memberInfo;
    }




    @Override
    public void insertMember(Member member, String[] roleIds) {

        member.setCreateTime(new Date());
        member.setPassword(ENCODER.encode(member.getPassword()));
        //保存用户
        memberMapper.insert(member);
        //绑定角色
        if(ArrayUtils.isNotEmpty(roleIds)){
            for(String rid : roleIds){
                MemberRole memberRole = new MemberRole();
                memberRole.setMemberId(member.getMemberId());
                memberRole.setRoleId(new Integer(rid));
                memberRoleMapper.insert(memberRole);
            }
        }
    }

    @Override
    public void updateMember(Member member, String[] roleIds) {
        member.setPassword(null);
        //更新用户
        memberMapper.updateById(member);
        //删除已有权限
        memberRoleMapper.delete(new QueryWrapper<MemberRole>().eq("member_id",member.getMemberId()));
        //重新绑定角色
        if(ArrayUtils.isNotEmpty(roleIds)){
            for(String rid : roleIds){
                MemberRole memberRole = new MemberRole();
                memberRole.setMemberId(member.getMemberId());
                memberRole.setRoleId(new Integer(rid));
                memberRoleMapper.insert(memberRole);
            }
        }

    }

    @Override
    public Member getById(Serializable id) {
        Long lid = Long.valueOf(id.toString().replace(",",""));
        return super.getById(lid);
    }

    /**
     * 保存用户验证码，和randomStr绑定
     *
     * @param randomStr 客户端生成
     * @param imageCode 验证码信息
     */
    @Override
    public void saveImageCode(String randomStr, String imageCode) {
        redisTemplate.opsForValue().set(SecurityConstants.DEFAULT_CODE_KEY + randomStr, imageCode, SecurityConstants.DEFAULT_IMAGE_EXPIRE, TimeUnit.SECONDS);
    }

    /**
     * 发送验证码
     * <p>
     * 1. 先去redis 查询是否 60S内已经发送
     * 2. 未发送： 判断手机号是否存 ? false :产生4位数字  手机号-验证码
     * 3. 发往消息中心-》发送信息
     * 4. 保存redis
     *
     * @param mobile 手机号
     * @return true、false
     */
    @Override
    public R<Boolean> sendSmsCode(String mobile) {
        Object tempCode = redisTemplate.opsForValue().get(SecurityConstants.DEFAULT_CODE_KEY + mobile);
        if (tempCode != null) {
            log.error("用户:{}验证码未失效{}", mobile, tempCode);
            return new R<>(false, "验证码未失效，请失效后再次申请");
        }

        Member params = new Member();
        params.setPhone(mobile);
        List<Member> memberList = this.list(new QueryWrapper<Member>(params));

        if (CollectionUtils.isEmpty(memberList)) {
            log.error("根据用户手机号{}查询用户为空", mobile);
            return new R<>(false, "手机号不存在");
        }

        String code = RandomStringUtils.random(4);
        JSONObject contextJson = new JSONObject();
        contextJson.put("code", code);
        contextJson.put("product", "Vole4Cloud");
        log.info("短信发送请求消息中心 -> 手机号:{} -> 验证码：{}", mobile, code);
//        rabbitTemplate.convertAndSend(MqQueueConstant.MOBILE_CODE_QUEUE,
//                new MobileMsgTemplate(
//                        mobile,
//                        contextJson.toJSONString(),
//                        CommonConstant.ALIYUN_SMS,
//                        EnumSmsChannelTemplate.LOGIN_NAME_LOGIN.getSignName(),
//                        EnumSmsChannelTemplate.LOGIN_NAME_LOGIN.getTemplate()
//                ));
        redisTemplate.opsForValue().set(SecurityConstants.DEFAULT_CODE_KEY + mobile, code, SecurityConstants.DEFAULT_IMAGE_EXPIRE, TimeUnit.SECONDS);
        return new R<>(true);
    }



}
