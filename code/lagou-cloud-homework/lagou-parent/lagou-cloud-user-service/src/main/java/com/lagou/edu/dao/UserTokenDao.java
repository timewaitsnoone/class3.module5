package com.lagou.edu.dao;


import com.lagou.edu.pojo.LagouToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTokenDao extends JpaRepository<LagouToken,Long> {
    public List<LagouToken> findByEmail(String email);
    public List<LagouToken> findByToken(String token);
    public Boolean existsByEmail(String email);
}
