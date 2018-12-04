package com.github.vole.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * spring-boot-mps:
 * code: https://github.com/codecentric/spring-boot-admin
 * doc: http://codecentric.github.io/spring-boot-admin/2.0.1/#getting-started
 * 		http://codecentric.github.io/spring-boot-admin/1.5.7/#getting-started (任支持整合Hystrix，Turbine，Activiti UI Module)
 */
@Slf4j
@EnableAdminServer        // 启用Admin模块
@EnableDiscoveryClient    // 用于服务注册和发现
@SpringBootApplication
public class VoleMonitorApplication {

    public static void main(String[] args) {
        log.debug("VoleMonitorApplication startup main");
        SpringApplication application = new SpringApplication(VoleMonitorApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }
}
