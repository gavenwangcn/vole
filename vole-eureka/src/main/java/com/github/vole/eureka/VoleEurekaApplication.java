package com.github.vole.eureka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@Slf4j
@EnableEurekaServer
@SpringBootApplication
public class VoleEurekaApplication {

    public static void main(String[] args) {
        log.debug("VoleEurekaApplication startup main");
        SpringApplication application = new SpringApplication(VoleEurekaApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }
}
