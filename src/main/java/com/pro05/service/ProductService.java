package com.pro05.service;

import com.pro05.entity.Category;
import com.pro05.entity.Product;
import com.pro05.entity.ProductVO;
import com.pro05.entity.*;
import com.pro05.util.Page;

import java.util.List;
import java.util.Map;

public interface ProductService {
    public List<ProductVO> productList(Page page);
    public int getCount(Page page);
    public ProductVO productDetail(Long pno);
    public List<ProductVO> productListBySeller(String seller, Page page);
    public int productCountBySeller(String seller, Page page);
    public int productInsert(Product product);
    public int productUpdate(Product product);
    public void productReserved(Long pno);
    public void productOut(Long pno);
    public void productSale(Long pno);
    public void productRemove(Long pno);
    public List<Category> categories();
    public List<Map<String, Integer>> getCateProCnt();
    int fileDataDelete(Long fileNo);
    List<ProductVO> popularProducts();
}
