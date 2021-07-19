package com.lagou.edu.pojo;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "lagou_token")
public class LagouToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String token;
    private String password;
}
