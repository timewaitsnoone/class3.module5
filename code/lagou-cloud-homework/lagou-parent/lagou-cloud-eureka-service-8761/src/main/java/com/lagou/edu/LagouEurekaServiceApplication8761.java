package com.lagou.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class LagouEurekaServiceApplication8761 {
    public static void main(String[] args) {
        SpringApplication.run(LagouEurekaServiceApplication8761.class,args);
    }
}
