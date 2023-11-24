package com.pro05.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishProduct {
    private Long pno;
    private String pname;
    private String seller;
    private int price;
    private String proaddr;
    private Long image;
    private Date createAt;
    private Date baseAt;
    private String status;
    private String cateName;
    private Long wno;

    //이미지
    private List<FileData> fileDataList;
}
