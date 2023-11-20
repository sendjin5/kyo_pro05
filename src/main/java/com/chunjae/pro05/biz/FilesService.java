package com.chunjae.pro05.biz;


import com.chunjae.pro05.entity.Files;

import java.util.List;

public interface FilesService {
    //Files
    public Files fileListByPar(String par) throws Exception;
    public void filesInsert(Files files) throws Exception;
    public void filesDeleteAll(String par) throws Exception;


}
