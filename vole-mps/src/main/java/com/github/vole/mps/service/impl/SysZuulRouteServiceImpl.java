package com.github.vole.mps.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.vole.common.constants.CommonConstant;
import com.github.vole.mps.mapper.SysZuulRouteMapper;
import com.github.vole.mps.model.entity.SysZuulRoute;
import com.github.vole.mps.service.SysZuulRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysZuulRouteServiceImpl extends ServiceImpl<SysZuulRouteMapper, SysZuulRoute> implements SysZuulRouteService {
    @Autowired
    private RedisTemplate redisTemplate;
//    @Autowired
//    private RabbitTemplate rabbitTemplate;

    /**
     * 立即生效配置
     *
     * @return
     */
    @Override
    public Boolean applyZuulRoute() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);
        List<SysZuulRoute> routeList = this.list(wrapper);
        redisTemplate.opsForValue().set(CommonConstant.ROUTE_KEY, routeList);
        //rabbitTemplate.convertAndSend(MqQueueConstant.ROUTE_CONFIG_CHANGE, 1);
        return Boolean.TRUE;
    }
}
