package com.github.vole.mps.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.vole.mps.model.entity.SysZuulRoute;
import com.github.vole.mps.service.SysZuulRouteService;
import com.github.vole.portal.common.controller.AbstractController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mps/route")
public class ZuulRouteController extends AbstractController<SysZuulRouteService,SysZuulRoute> {

    @Override
    protected String getTemplatePath() {
        return "ftl/mps/route/";
    }

    @Override
    protected QueryWrapper<SysZuulRoute> getSearchQW(String search, Model model) {
        QueryWrapper<SysZuulRoute> qw = new QueryWrapper<SysZuulRoute>();
        if(StringUtils.isNotBlank(search)){
            qw.like("service_id",search);
            model.addAttribute("service_id",search);
        }
        return qw;
    }
}
