package com.pro05.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private Long nno;
    private String tableName;
    private Long columnNo;
    private String word;
    private String message;
    private String uid;         // member.id
    private String status = "UNREAD";
}
