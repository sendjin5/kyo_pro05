package com.chunjae.pro05.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Files {
    private int fno;
    private String par;
    private String saveFolder;
    private String originNm;
    private String saveNm;
    private String fileType;
    private String uploadDate;
}