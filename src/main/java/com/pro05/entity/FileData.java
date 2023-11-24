package com.pro05.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileData {
    private Long fileNo;
    private String tableName;
    private Long columnNo;
    private String originName;
    private String saveName;
    private String savePath;
    private String fileType;
    private String status = "ACTIVE";
}
