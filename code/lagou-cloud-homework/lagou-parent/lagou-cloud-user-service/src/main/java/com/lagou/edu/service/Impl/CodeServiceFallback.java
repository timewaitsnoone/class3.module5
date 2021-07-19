package com.lagou.edu.service.Impl;

import com.lagou.edu.service.CodeServiceFeignClient;
import org.springframework.stereotype.Component;

@Component
public class CodeServiceFallback implements CodeServiceFeignClient {
    @Override
    public boolean createCode(String email) {
        return false;
    }

    @Override
    public int validateCode(String email, String code) {
        return -1;
    }
}
