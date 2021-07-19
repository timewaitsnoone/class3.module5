package com.lagou.edu.service.Impl;

import com.lagou.edu.service.EmailServiceFeignClient;
import org.springframework.stereotype.Component;

@Component
public class EmailFallback implements EmailServiceFeignClient {
    @Override
    public Boolean sendEmail(String email, String code) {
        return false;
    }
}
