package com.tpp.threat_perception_platform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@MapperScan("com.tpp.threat_perception_platform.mapper")
public class ThreatPerceptionPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThreatPerceptionPlatformApplication.class, args);
    }

}
