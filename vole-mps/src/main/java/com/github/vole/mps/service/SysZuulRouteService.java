package com.github.vole.mps.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.vole.mps.model.entity.SysZuulRoute;


public interface SysZuulRouteService extends IService<SysZuulRoute> {

    /**
     * 立即生效配置
     * @return
     */
    Boolean applyZuulRoute();
}
