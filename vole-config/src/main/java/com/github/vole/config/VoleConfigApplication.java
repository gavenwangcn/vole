package com.github.vole.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@Slf4j
@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication
public class VoleConfigApplication {

    public static void main(String[] args) {
        log.debug("VoleConfigApplication startup main");
        SpringApplication application = new SpringApplication(VoleConfigApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }
}
