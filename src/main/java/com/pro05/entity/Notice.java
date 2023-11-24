package com.pro05.entity;

import lombok.Data;

@Data
public class Notice {
    private int no;
    private String title;
    private String content;
    private String author;
    private String img;
    private String resdate;
}
