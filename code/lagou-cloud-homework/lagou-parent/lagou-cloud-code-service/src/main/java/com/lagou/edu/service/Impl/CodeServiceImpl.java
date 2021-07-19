package com.lagou.edu.service.Impl;

import com.lagou.edu.dao.LagouAuthCodeDao;
import com.lagou.edu.pojo.LagouAuthCode;
import com.lagou.edu.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CodeServiceImpl implements CodeService {
    @Autowired
    private LagouAuthCodeDao lagouAuthCodeDao;

    @Override
    public String createCode(String email) {
        String code= String.valueOf((int)((Math.random()*9+1)*100000));

        LagouAuthCode lagouAuthCode = new LagouAuthCode();
        lagouAuthCode.setCode(code);
        lagouAuthCode.setEmail(email);
        Date currDate = new Date();
        lagouAuthCode.setCreatetime(currDate);
        currDate.setTime(currDate.getTime()+10*60*1000);
        lagouAuthCode.setExpiretime(currDate);
        LagouAuthCode save = lagouAuthCodeDao.save(lagouAuthCode);

        return code;
    }

    @Override
    public int validCode(String email, String code) {

        LagouAuthCode lagouAuthCode = new LagouAuthCode();
        lagouAuthCode.setEmail(email);
        Example<LagouAuthCode> example=Example.of(lagouAuthCode);
        List<LagouAuthCode> all = lagouAuthCodeDao.findAll(example);
        Date date = new Date();
        for(LagouAuthCode item :all){
            if(item.getCode().equals(code)){
                if(item.getExpiretime().getTime()<date.getTime()){
                    return 2;
                }
                return 0;
            }
        }

        return 1;
    }
}
