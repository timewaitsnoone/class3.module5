package com.lagou.edu.service;

import com.lagou.edu.service.fallback.UserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "lagou-service-user",path = "/user")
public interface UserServiceFeignClient {
    @RequestMapping("/info/{token}")
    public String info( @PathVariable("token") String token);
    @RequestMapping("/isRegister/{email}")
    public Boolean isRegister(@PathVariable("email") String email);
}
