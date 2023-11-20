package com.chunjae.pro05.biz;


import com.chunjae.pro05.entity.Product;
import com.chunjae.pro05.util.Page;

import java.util.List;

public interface ProductSerivce {
    public List<Product> productAllList();
    public List<Product> productSelList(String addr1) throws Exception;
    public List<Product> productTemList();
    public Product productGet(int pno);
    public void productAdd(Product product);
    public void productDel(int pno);
    public List<Product> productPage(Page page);
}
