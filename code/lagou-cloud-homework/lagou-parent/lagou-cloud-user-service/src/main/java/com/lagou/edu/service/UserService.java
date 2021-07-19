package com.lagou.edu.service;

public interface UserService {
    public Boolean register(String email,String password,String code);
    public Boolean isRegister(String email);
    public String login(String email,String password);
    public String parseToken(String token);
    public String createToken(String email,String password);
}
