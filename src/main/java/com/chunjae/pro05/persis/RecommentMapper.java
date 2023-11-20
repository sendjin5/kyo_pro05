package com.chunjae.pro05.persis;


import com.chunjae.pro05.entity.Recomment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface RecommentMapper {
    @Select("select * from recomment")
    public List<Recomment> recommentList();

    @Insert("insert into recomment values(default, #{id_no}, #{comment})")
    public void recommentAdd(Recomment recomment);

    @Delete("delete from recomment where no=#{no}")
    public void recommentDel(int no);


}
