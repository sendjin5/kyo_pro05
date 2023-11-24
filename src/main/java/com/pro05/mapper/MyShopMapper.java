package com.pro05.mapper;

import com.pro05.entity.ProductVO;
import com.pro05.util.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MyShopMapper {
    @Select({
            "<script>",
            "SELECT * from productWithCate",
            "WHERE seller = #{seller}",
            "<if test='page.searchType != null and page.searchType != \"\"'>",
            "   AND ${page.searchType} LIKE CONCAT('%', #{page.searchKeyword}, '%')",
            "</if>",
            "ORDER BY pno DESC LIMIT #{page.postStart}, #{page.postScreen}",
            "</script>"
    })
    List<ProductVO> productListBySeller(@Param("seller") String seller, @Param("page") Page page);

    @Select({
            "<script>",
            "SELECT COUNT(*) FROM product WHERE seller = #{seller}",
            "<if test='page.searchType != null and page.searchType != \"\"'>",
            "   AND ${page.searchType} LIKE CONCAT('%', #{page.searchKeyword}, '%')",
            "</if>",
            "ORDER BY pno DESC LIMIT #{page.postStart}, #{page.postScreen}",
            "</script>"
    })
    int productCountBySeller(@Param("seller") String seller, @Param("page") Page page);

    @Select("SELECT * FROM productWithCate WHERE status='SALE' ORDER BY heart DESC LIMIT 9;")
    List<ProductVO> popularProducts();
}
