package com.pro05.service;

import com.pro05.entity.Keyword;
import com.pro05.util.Page;

import java.util.List;

public interface KeywordService {
    List<Keyword> keywordsByUid(String uid, Page page);

    int keywordCountByUid(String uid, Page page);

    void keywordInsert(Keyword keyword);

    void keywordUpdate(Keyword keyword);

    void keywordDelete(Long kno);
}
