package com.github.vole.mps.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.github.vole.mps.mapper.RoleMapper;
import com.github.vole.mps.model.entity.Role;
import com.github.vole.mps.service.RoleService;
import org.springframework.stereotype.Service;



@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {


}
