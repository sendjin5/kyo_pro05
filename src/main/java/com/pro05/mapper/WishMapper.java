package com.pro05.mapper;

import com.pro05.entity.Wish;
import com.pro05.entity.WishProduct;
import com.pro05.util.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WishMapper {
    @Select("SELECT * FROM wish WHERE uid=#{uid}")
    List<Wish> wishList(String uid);

    @Select("SELECT * FROM wish WHERE wno=#{wno}")
    Wish wishGet(Long wno);

    @Select({
            "<script>",
            "SELECT p.pno, pname, seller, price, proaddr, image, createAt, baseAt, p.status, cateName, wno",
            "FROM productWithCate p",
            "JOIN wish w ON p.pno = w.pno",
            "WHERE w.uid = #{uid}",
            "<if test='page.searchType != null and page.searchType != \"\"'>",
            "   AND ${page.searchType} LIKE CONCAT('%', #{page.searchKeyword}, '%')",
            "</if>",
            "ORDER BY wno DESC LIMIT #{page.postStart}, #{page.postScreen}",
            "</script>"
    })
    List<WishProduct> wishProductList(@Param("uid") String uid, @Param("page") Page page);

    @Select({
            "<script>",
            "SELECT COUNT(*) FROM productWithCate p",
            "JOIN wish w ON p.pno = w.pno",
            "WHERE w.uid = #{uid}",
            "<if test='page.searchType != null and page.searchType != \"\"'>",
            "   AND ${page.searchType} LIKE CONCAT('%', #{page.searchKeyword}, '%')",
            "</if>",
            "ORDER BY wno DESC LIMIT #{page.postStart}, #{page.postScreen}",
            "</script>"
    })
    int wishProductCount(@Param("uid") String uid, @Param("page") Page page);


    @Select("SELECT count(*) FROM wish WHERE pno=#{pno} AND uid=#{uid}")
    int wishFind(@Param("pno") Long pno, @Param("uid") String uid);

    @Select("SELECT heart FROM product WHERE pno=#{pno}")
    int wishCount(Long pno);

    @Insert("INSERT INTO wish VALUES (default, #{pno}, #{uid}, 1)")
    int wishInsert(Wish wish);

    @Delete("DELETE FROM wish WHERE pno=#{pno} AND uid=#{uid}")
    int wishDelete(Wish wish);

    @Update("UPDATE product set heart=heart+1 where pno=#{pno}")
    void increaseWish(Long pno);

    @Update("UPDATE product set heart=heart-1 where pno=#{pno}")
    void decreaseWish(Long pno);

    @Delete("DELETE FROM wish WHERE wno=#{wno}")
    void wishRemove(Long wno);

}
