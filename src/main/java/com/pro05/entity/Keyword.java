package com.pro05.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Keyword {
    private Long kno;
    private String word;    // 키워드
    private String uid;     // member.id
}
