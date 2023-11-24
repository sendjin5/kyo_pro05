package com.pro05.service;


import com.pro05.entity.Recomment;

import java.util.List;


public interface RecommentService {
    public List<Recomment> recommentList(String mem_id);
    Recomment recommentOne(int no);
    public void recommentAdd(Recomment recomment);
    public void recommentDel(int no);


}
