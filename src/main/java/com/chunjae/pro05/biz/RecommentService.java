package com.chunjae.pro05.biz;

import com.chunjae.pro05.entity.Recomment;
import com.chunjae.pro05.persis.RecommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommentService {

    @Autowired
    private RecommentMapper recommentMapper;

    public List<Recomment> recommentList(){
     return recommentMapper.recommentList();
    }

    public void recommentAdd(Recomment recomment){
        recommentMapper.recommentAdd(recomment);

    };
    public void recommentDel(int no){
        recommentMapper.recommentDel(no);
    };
}
