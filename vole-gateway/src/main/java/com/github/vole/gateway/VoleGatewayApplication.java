package com.github.vole.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Slf4j
@EnableZuulProxy
@EnableFeignClients
@EnableDiscoveryClient
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableHystrix                // 开启断路器
@EnableHystrixDashboard
@SpringBootApplication(scanBasePackages = {"com.github.vole.gateway", "com.github.vole.common.bean"})
public class VoleGatewayApplication {

    public static void main(String[] args) {
        log.debug("VoleGatewayApplication startup main");
        SpringApplication application = new SpringApplication(VoleGatewayApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }
}
