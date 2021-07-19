package com.lagou.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
//@EntityScan("com.lagou.edu.pojo.LagouAuthCode")
@EnableFeignClients
public class LagouCodeApplication {
    public static void main(String[] args) {
        SpringApplication.run(LagouCodeApplication.class,args);
    }
}
