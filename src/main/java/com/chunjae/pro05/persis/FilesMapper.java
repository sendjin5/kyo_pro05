package com.chunjae.pro05.persis;

import com.chunjae.pro05.entity.Files;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface FilesMapper {

    //Files
    @Select("SELECT * FROM files WHERE par=#{par}")
    Files fileListByPar(String par) throws Exception;

    @Insert("INSERT INTO files VALUES (DEFAULT, #{par}, #{saveFolder}, #{originNm}, #{saveNm}, #{fileType}, DEFAULT)")
    void filesInsert(Files files) throws Exception;

    @Delete("DELETE FROM files WHERE par=#{par}")
    void filesDeleteAll(String par) throws Exception;


}