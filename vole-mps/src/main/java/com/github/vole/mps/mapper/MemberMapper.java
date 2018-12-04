package com.github.vole.mps.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.vole.common.config.db.Query;
import com.github.vole.mps.model.entity.Member;
import com.github.vole.mps.model.vo.MemberVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface MemberMapper extends BaseMapper<Member> {

}