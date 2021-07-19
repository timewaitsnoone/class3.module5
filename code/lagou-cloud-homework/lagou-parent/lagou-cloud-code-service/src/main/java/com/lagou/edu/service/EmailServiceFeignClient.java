package com.lagou.edu.service;

import com.lagou.edu.service.Impl.EmailFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "lagou-cloud-email-service",fallback = EmailFallback.class,path = "/email")
public interface EmailServiceFeignClient {
    @RequestMapping("/{email}/{code}")
    public Boolean sendEmail(@PathVariable("email") String email, @PathVariable("code") String code);
}
