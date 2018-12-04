package com.github.vole.portal;

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
@EnableFeignClients
@EnableHystrix                // 开启断路器
@EnableHystrixDashboard
@SpringBootApplication(scanBasePackages = {"com.github.vole.portal", "com.github.vole.common.bean"})
public class VolePortalApplication {

	public static void main(String[] args) {
		log.debug("VolePortalApplication startup main");
		SpringApplication application = new SpringApplication(VolePortalApplication.class);
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
	}
}
