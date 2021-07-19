package com.lagou.edu.service.fallback;

import com.lagou.edu.service.UserServiceFeignClient;
import org.springframework.stereotype.Component;

//@Component
public class UserServiceFallback implements UserServiceFeignClient {
    @Override
    public String info(String token) {
        return "";
    }

    @Override
    public Boolean isRegister(String email) {
        return false;
    }
}
