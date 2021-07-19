package com.lagou.edu.dao;

import com.lagou.edu.pojo.LagouAuthCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface LagouAuthCodeDao extends JpaRepository<LagouAuthCode,Long>, JpaSpecificationExecutor<LagouAuthCode> {
}
