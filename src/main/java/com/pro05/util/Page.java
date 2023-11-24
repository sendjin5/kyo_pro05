package com.pro05.util;

import lombok.Data;

@Data
public class Page {
    private int pageScreen = 5; // 한 화면 당 페이지 개수
    private int pageStart; // 현재 화면의 시작 페이지 번호
    private int pageLast; // 현재 화면의 마지막 페이지 번호
    private int pageNow = 1; // 현재 화면의 현재 페이지 번호
    private int pageTotal; // 전체 페이지 개수
    private int postScreen = 10; // 한 화면(페이지) 당 게시글 개수
    private int postStart; // 현재 페이지블록의 첫 번째 게시글 인덱스
    private int postTotal; // 전체 게시글 개수
    private String searchType;
    // 검색 타입(컬럼명과 일치해야 함) ex. 제목(title), 내용(content) 등
    private String searchKeyword; // 검색 키워드

    // Product
    private String category;
    private Long pno;
    private String proaddr;
    private String status;


    public void makePage(){
        // ex. 1페이지의 첫 번째 게시글 인덱스는 0, 2페이지의 첫 번째 게시글 인덱스는 10
        this.postStart = (this.pageNow - 1) * this.postScreen;

        if(this.postTotal <= 1){
            // 게시글 개수가 1인 경우에는 총 페이지를 1로 제한(DivideByZeroException 방지)
            this.pageTotal = 1;
        } else {
            // ex. 페이지당 게시글 개수 10개일 때, 게시글 개수가 10개인 경우 총 페이지는 1개
            // ex2. 페이지당 게시글 개수 10개일 때, 게시글 개수가 12개인 경우 총 페이지는 2개
            this.pageTotal = ((this.postTotal - 1) / this.postScreen) + 1;
        }

        if(this.pageNow<=1){
            // 현재 페이지가 0 또는 1인 경우 시작 페이지를 1로 제한 (DivideByZeroException 방지)
            this.pageStart = 1;
        } else {
            this.pageStart = ((this.pageNow - 1) / this.pageScreen) * this.pageScreen + 1;
        }

        // ex. 시작페이지가 1이면, 마지막페이지는 5
        this.pageLast = this.pageStart + this.pageScreen - 1;
        if(this.pageLast > this.pageTotal) {
            // ex. 만약 전체 페이지가 32고, 시작 페이지가 31이라면, 마지막 페이지는 35가 아니라 32가 되어야 한다.
            this.pageLast = this.pageTotal;
        }
    }
}