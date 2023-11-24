package com.pro05.mapper;

import com.pro05.entity.Category;
import com.pro05.entity.Product;
import com.pro05.entity.ProductVO;
import com.pro05.util.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductMapper {
    List<ProductVO> productList(Page page);
    int getCount(Page page);
    ProductVO productDetail(Long pno);
    int productInsert(Product product);
    int productUpdate(Product product);
    void productReserved(Long pno);
    void productOut(Long pno);
    void productSale(Long pno);
    void productRemove(Long pno);
    List<Category> categories();
    List<Map<String, Integer>> getCateProCnt();
    int productGetLast();

    int fileDataDelete(Long fileNo);

}
