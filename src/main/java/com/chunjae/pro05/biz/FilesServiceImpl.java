package com.chunjae.pro05.biz;

import com.chunjae.pro05.entity.Files;
import com.chunjae.pro05.persis.FilesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilesServiceImpl implements FilesService {

    @Autowired
    private FilesMapper filesMapper;

    @Override
    public void filesInsert(Files files) throws Exception {
        filesMapper.filesInsert(files);
    }

    @Override
    public void filesDeleteAll(String par) throws Exception {
        filesMapper.filesDeleteAll(par);
    }

    @Override
    public Files fileListByPar(String par) throws Exception {
        return filesMapper.fileListByPar(par);
    }
}