package com.lagou.edu.controller;

import com.lagou.edu.dao.MailVo;
import com.lagou.edu.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class MailController {
    @Autowired
    private MailService mailService;

    @Value("${spring.mail.username}")
    private String thisMail;

    @RequestMapping("/{email}/{code}")
    public Boolean sendEmail(@PathVariable String email,@PathVariable String code){
        MailVo mailVo = new MailVo();
        mailVo.setTo(email);
        mailVo.setFrom(thisMail);
        mailVo.setText("您的验证码是 : "+code+",有效期3分钟.");
        mailVo.setSubject("您的验证码是 : "+code+",有效期3分钟.");
        return  mailService.sendMail(mailVo).getStatus().equals("ok");
    }
}
