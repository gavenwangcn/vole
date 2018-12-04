package com.github.vole.mps;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

@Slf4j
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.github.vole.portal.common.fegin"})
@EnableHystrix                // 开启断路器
@EnableHystrixDashboard
@SpringBootApplication(scanBasePackages = {"com.github.vole.mps", "com.github.vole.common.bean","com.github.vole.portal.common"})
public class VoleMpsApplication {

    public static void main(String[] args) {
        log.debug("VoleMpsApplication startup main");
        SpringApplication application = new SpringApplication(VoleMpsApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }
}
