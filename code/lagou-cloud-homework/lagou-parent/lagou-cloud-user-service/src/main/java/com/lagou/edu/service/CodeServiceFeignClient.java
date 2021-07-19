package com.lagou.edu.service;


import com.lagou.edu.service.Impl.CodeServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "lagou-service-code", fallback = CodeServiceFallback.class, path = "/code")
public interface CodeServiceFeignClient {
    @RequestMapping("/create/{email}")
    public boolean createCode(@PathVariable("email") String email);

    @RequestMapping("/validate/{email}/{code}")
    public int validateCode(@PathVariable("email") String email, @PathVariable("code") String code);
}
