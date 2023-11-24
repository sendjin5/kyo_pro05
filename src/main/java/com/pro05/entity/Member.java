package com.pro05.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    private Long mno;
    private String id;
    private String pw;
    private String name;
    private String tel;
    private String email;
    private String addr1;
    private String addr2;
    private String addr3;
    private String postcode;
    private String status = "ACTIVE";
    private Date createAt;
    private Date loginAt;
}
