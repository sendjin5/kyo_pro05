package com.chunjae.pro05.persis;


import com.chunjae.pro05.entity.Product;
import com.chunjae.pro05.util.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface ProductMapper {
    @Select("select * from product where status=1 order by ingDate desc")
    public List<Product> productAllList();

    @Select("select * from product where addr1='가산동' and status=1 order by ingDate desc")
    public List<Product> productSelList(String addr1) throws Exception;

    @Select("select * from product where status=0")
    public List<Product> productTemList();

    @Select("select * from product where pno=#{pno}")
    public Product productGet(int pno);

    @Insert("insert into product values(default, #{userNo}, #{userId}, #{title}, #{cate}, #{content}, default, default, #{thumbnail}, #{price}, #{addr1}, default, default)")
    public void productAdd(Product product);

    @Delete("delete from product where pno=#{pno}")
    public void productDel(int pno);



    @Select({"<script>","SELECT * FROM product" +
            "<if test='searchType != null and searchType != \"\"'> WHERE ${searchType} LIKE CONCAT('%', #{searchKeyword}, '%')" +
            "</if>" +
            " ORDER BY resdate ASC LIMIT #{postStart}, #{postCount}", "</script>"})
    public List<Product> productPage(Page page);
}
