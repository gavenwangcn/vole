package com.github.vole.mps.model.dto;

import com.github.vole.mps.model.entity.Member;
import lombok.Data;

import java.util.List;


@Data
public class MemberDTO extends Member {
    /**
     * 角色ID
     */
    private List<Integer> role;

    /**
     * 新密码
     */
    private String newpassword1;
}
