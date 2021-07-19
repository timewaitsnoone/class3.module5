package com.lagou.edu.service.Impl;

import com.lagou.edu.dao.UserTokenDao;
import com.lagou.edu.pojo.LagouToken;
import com.lagou.edu.service.CodeServiceFeignClient;
import com.lagou.edu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserTokenDao userTokenDao;

    @Autowired
    private CodeServiceFeignClient codeServiceFeignClient;

    @Override
    public Boolean register(String email, String password, String code) {
        int isVaild = codeServiceFeignClient.validateCode(email, code);
        if(isVaild!=0) {
            return false;
        }
        LagouToken lagouToken = new LagouToken();
        lagouToken.setEmail(email);
        lagouToken.setToken(UUID.randomUUID().toString());
        lagouToken.setPassword(password);
        LagouToken save = userTokenDao.save(lagouToken);

        return true;
    }

    @Override
    public Boolean isRegister(String email) {
        return userTokenDao.existsByEmail(email);
    }

    @Override
    public String login(String email, String password) {
        LagouToken lagouToken = new LagouToken();
        lagouToken.setPassword(password);
        lagouToken.setEmail(email);
        Example<LagouToken> example=Example.of(lagouToken);
        Optional<LagouToken> one = userTokenDao.findOne(example);
        if(!one.isEmpty()){
            return one.get().getEmail();
        }
        return "";
    }

    @Override
    public String parseToken(String token) {
        List<LagouToken> byToken = userTokenDao.findByToken(token);
        if(byToken.size()==1){
            return byToken.get(0).getEmail();
        }
        return "";
    }

    @Override
    public String createToken(String email, String password) {
        String token = UUID.randomUUID().toString();
        LagouToken lagouToken = userTokenDao.findByEmail(email).get(0);
        lagouToken.setToken(token);
        LagouToken save = userTokenDao.save(lagouToken);
        return save.getToken();
    }
}
