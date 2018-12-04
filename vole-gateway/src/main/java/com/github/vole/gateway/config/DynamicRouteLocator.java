package com.github.vole.gateway.config;

import com.github.vole.common.constants.CommonConstant;
import com.github.vole.common.vo.SysZuulRouteVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;

/**
 * 动态路由实现
 */
@Slf4j
public class DynamicRouteLocator extends DiscoveryClientRouteLocator {
    private ZuulProperties properties;
    private RedisTemplate redisTemplate;

    public DynamicRouteLocator(String servletPath, DiscoveryClient discovery, ZuulProperties properties,
                               ServiceInstance localServiceInstance, RedisTemplate redisTemplate) {
        super(servletPath, discovery, properties, localServiceInstance);
        this.properties = properties;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 重写路由配置
     * <p>
     * 1. properties 配置。
     * 2. eureka 默认配置。
     * 3. DB数据库配置。
     *
     * @return 路由表
     */
    @Override
    protected LinkedHashMap<String, ZuulProperties.ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = new LinkedHashMap<>();
        //读取properties配置、eureka默认配置
        routesMap.putAll(super.locateRoutes());
        log.debug("初始默认的路由配置完成");
        routesMap.putAll(locateRoutesFromDb());
        LinkedHashMap<String, ZuulProperties.ZuulRoute> values = new LinkedHashMap<>();
        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : routesMap.entrySet()) {
            String path = entry.getKey();
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (StringUtils.isNotBlank(this.properties.getPrefix())) {
                path = this.properties.getPrefix() + path;
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }
            values.put(path, entry.getValue());
        }
        return values;
    }

    /**
     * Redis中保存的，没有从mps拉去，避免启动链路依赖问题（取舍），网关依赖业务模块的问题
     *
     * @return
     */
    private Map<String, ZuulProperties.ZuulRoute> locateRoutesFromDb() {
        Map<String, ZuulProperties.ZuulRoute> routes = new LinkedHashMap<>();

        Object obj = redisTemplate.opsForValue().get(CommonConstant.ROUTE_KEY);
        if (obj == null) {
            return routes;
        }

        List<SysZuulRouteVO> results = (List<SysZuulRouteVO>) obj;
        for (SysZuulRouteVO result : results) {
            if (StringUtils.isBlank(result.getPath()) && StringUtils.isBlank(result.getUrl())) {
                continue;
            }

            ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
            try {
                zuulRoute.setId(result.getServiceId());
                zuulRoute.setPath(result.getPath());
                zuulRoute.setServiceId(result.getServiceId());
                zuulRoute.setRetryable(StringUtils.equals(result.getRetryable(), "0") ? Boolean.FALSE : Boolean.TRUE);
                zuulRoute.setStripPrefix(StringUtils.equals(result.getStripPrefix(), "0") ? Boolean.FALSE : Boolean.TRUE);
                zuulRoute.setUrl(result.getUrl());
                String[] sensitiveHeaders = StringUtils.split(result.getSensitiveheadersList(), ",");
                List sensitiveHeadersList = new ArrayList();
                CollectionUtils.addAll(sensitiveHeadersList, sensitiveHeaders);
                if (sensitiveHeadersList != null) {
                    Set<String> sensitiveHeaderSet = new HashSet<String>();
                    sensitiveHeadersList.forEach(sensitiveHeader -> sensitiveHeaderSet.add(sensitiveHeader.toString().trim()));
                    zuulRoute.setSensitiveHeaders(sensitiveHeaderSet);
                    zuulRoute.setCustomSensitiveHeaders(true);
                }
            } catch (Exception e) {
                log.error("从数据库加载路由配置异常", e);
            }
            log.debug("添加数据库自定义的路由配置,path：{}，serviceId:{}", zuulRoute.getPath(), zuulRoute.getServiceId());
            routes.put(zuulRoute.getPath(), zuulRoute);
        }
        return routes;
    }
}
