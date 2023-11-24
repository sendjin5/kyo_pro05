package com.pro05.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVO {
    private Long pno;
    private String pname;
    private String content;
    private String cate;
    private String cateName;
    private String seller;
    private int price = 0;
    private String proaddr;
    private Long image;
    private Date createAt;
    private Date baseAt;
    private String status = "SALE";
    private int visited = 0;
    private int heart;

    //이미지
    private List<FileData> fileDataList;
}
