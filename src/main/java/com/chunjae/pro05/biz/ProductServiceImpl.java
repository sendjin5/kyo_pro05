package com.chunjae.pro05.biz;

import com.chunjae.pro05.entity.Product;
import com.chunjae.pro05.persis.ProductMapper;
import com.chunjae.pro05.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductSerivce{

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> productAllList() {
        return productMapper.productAllList();
    }

    @Override
    public List<Product> productSelList(String addr1) throws Exception{
        return productMapper.productSelList(addr1);
    }

    @Override
    public List<Product> productTemList() {
        return productMapper.productTemList();
    }

    @Override
    public Product productGet(int pno) {
        return productMapper.productGet(pno);
    }

    @Override
    public void productAdd(Product product) {
        productMapper.productAdd(product);
    }

    @Override
    public void productDel(int pno) {
        productMapper.productDel(pno);
    }

    @Override
    public List<Product> productPage(Page page) {
        return productMapper.productPage(page);
    }
}
