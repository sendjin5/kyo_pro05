package com.pro05.mapper;


import com.pro05.entity.Recomment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RecommentMapper {
    @Select("select * from recomment where mem_id=#{mem_id}")
    List<Recomment> recommentList(String mem_id);

    @Select("select * from recomment where no=#{no}")
    Recomment recommentOne(int no);

    @Insert("insert into recomment values(default, #{mem_id}, #{comment})")
    void recommentAdd(Recomment recomment);

    @Delete("delete from recomment where no=#{no}")
    void recommentDel(int no);


}