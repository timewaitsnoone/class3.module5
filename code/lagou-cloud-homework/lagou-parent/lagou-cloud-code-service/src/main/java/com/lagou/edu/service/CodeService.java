package com.lagou.edu.service;

public interface CodeService {
    public  String createCode(String email);
    public int validCode(String email,String code);
}
