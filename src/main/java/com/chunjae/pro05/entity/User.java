package com.chunjae.pro05.entity;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String loginId;
    private String userName;
    private String password;
    private String passwordConfirm;
    private String email;
    private String tel;
    private String addr1;
    private String addr2;
    private String postcode;
    private String regDate;
    private String birth;
    private String grade;
    private int active;
    private int point;
    private int visited;
}
