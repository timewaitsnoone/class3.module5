package com.lagou.edu.controller;


import com.lagou.edu.service.CodeService;
import com.lagou.edu.service.EmailServiceFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/code")
public class CodeController {
    @Autowired
    private CodeService codeService;

    @Autowired
    private EmailServiceFeignClient emailServiceFeignClient;

    @RequestMapping("/create/{email}")
    public  boolean createCode(@PathVariable String email){
        String code = codeService.createCode(email);
        Boolean issuccess = emailServiceFeignClient.sendEmail(email, code);
        return issuccess;
    }

    @RequestMapping("/validate/{email}/{code}")
    public  int validateCode(@PathVariable String email,@PathVariable String code){
        int flag = codeService.validCode(email, code);
        return flag;
    }
}
