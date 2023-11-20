package com.chunjae.pro05.entity;

import lombok.Data;

@Data
public class Board {
    private int no;
    private String title;
    private String content;
    private String author;
    private String bfile;
    private String resdate;
}
