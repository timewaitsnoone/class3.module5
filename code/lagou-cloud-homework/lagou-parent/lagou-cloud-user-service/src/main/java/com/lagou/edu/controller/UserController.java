package com.lagou.edu.controller;

import com.lagou.edu.dao.UserTokenDao;
import com.lagou.edu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpHeaders;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/register/{email}/{password}/{code}")
    public Boolean register(@PathVariable String email,@PathVariable String password,@PathVariable String code){
        Boolean register = userService.register(email, password, code);
        return register;
    }

    @RequestMapping("/isRegister/{email}")
    public Boolean isRegister(@PathVariable String email){
        return userService.isRegister(email);
    }

    @RequestMapping("/login/{email}/{password}")
    public String login(@PathVariable String email,@PathVariable String password,HttpServletResponse response){
        String login = userService.login(email, password);
        String token = userService.createToken(email, password);
        Cookie tokenCookie = new Cookie("token", token);
        tokenCookie.setDomain("www.test.com");
        tokenCookie.setPath("/");
        response.addCookie(tokenCookie);
        response.setContentType("text/html");
        return login;
    }

    @RequestMapping("/info/{token}")
    public String info( @PathVariable String token){
        return userService.parseToken(token);
    }

}
