package com.lagou.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EmailServiceApplication28082 {
    public static void main(String[] args) {
        SpringApplication.run(EmailServiceApplication28082.class,args);
    }
}
