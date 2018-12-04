package com.github.vole.mps.listener;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.vole.common.constants.CommonConstant;
import com.github.vole.common.vo.SysZuulRouteVO;
import com.github.vole.mps.model.entity.SysZuulRoute;
import com.github.vole.mps.service.SysZuulRouteService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
public class RouteConfigInitListener{
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private SysZuulRouteService sysZuulRouteService;

    /**
     * Callback used to run the interceptor.
     * 初始化路由配置的数据，避免gateway 依赖业务模块
     *
     */
    @EventListener(value = {ApplicationStartedEvent.class})
    public void init() {
        log.info("开始初始化路由配置数据");
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);
        List<SysZuulRoute> routeList = sysZuulRouteService.list(wrapper);
        if (CollectionUtils.isNotEmpty(routeList)) {
            List<SysZuulRouteVO> routeVOS = new ArrayList<SysZuulRouteVO>();
            routeList.forEach(input ->{
                SysZuulRouteVO vo  =new SysZuulRouteVO();
                BeanUtils.copyProperties(input,vo);
                routeVOS.add(vo);
            });
            redisTemplate.opsForValue().set(CommonConstant.ROUTE_KEY, routeVOS);
            log.info("更新Redis中路由配置数据：{}条", routeVOS.size());
        }
        log.info("初始化路由配置数据完毕");
    }
}