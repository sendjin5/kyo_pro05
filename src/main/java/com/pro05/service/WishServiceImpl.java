package com.pro05.service;

import com.pro05.entity.Wish;
import com.pro05.entity.WishProduct;
import com.pro05.mapper.FileDataMapper;
import com.pro05.mapper.WishMapper;
import com.pro05.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishServiceImpl implements WishService {
    @Autowired
    private WishMapper wishMapper;
    @Autowired
    private FileDataMapper fileDataMapper;

    @Override
    public List<Wish> wishList(String uid) {
        return wishMapper.wishList(uid);
    }

    @Override
    public Wish wishGet(Long wno) {
        return wishMapper.wishGet(wno);
    }

    @Override
    public List<WishProduct> wishProductList(String uid, Page page) {
        List<WishProduct> list = new ArrayList<>();
        for (WishProduct p : wishMapper.wishProductList(uid, page)) {
            p.setFileDataList(fileDataMapper.fileDataBoardList("product", p.getPno()));
            list.add(p);
        }
        //return wishMapper.wishProductList(uid, page);
        return list;
    }

    @Override
    public int wishProductCount(String uid, Page page) {
        return wishMapper.wishProductCount(uid, page);
    }

    @Override
    public int wishFind(Long pno, String uid) {
        return wishMapper.wishFind(pno, uid);
    }

    @Override
    public int wishCount(Long pno) {
        return wishMapper.wishCount(pno);
    }

    @Override
    public int wishInsert(Wish wish) {
        return wishMapper.wishInsert(wish);
    }

    @Override
    public int wishDelete(Wish wish) {
        return wishMapper.wishDelete(wish);
    }

    @Override
    public void increaseWish(Long pno) {
        wishMapper.increaseWish(pno);
    }

    @Override
    public void decreaseWish(Long pno) {
        wishMapper.decreaseWish(pno);
    }

    @Override
    public void wishRemove(Long wno) {
        wishMapper.wishRemove(wno);
    }

    @Override
    public int checkWish(Wish wish) {
        //System.out.println("wish : " + wish);
        Long pno = wish.getPno();
        String uid = wish.getUid();
        int found = wishFind(pno, uid);
        //System.out.println("found : " + found);
        int result = 0;
        if (found == 0) {
            result = wishInsert(wish);
            increaseWish(pno);
        } else {
            result = wishDelete(wish) * -1;
            decreaseWish(pno);
        }
        return result;
    }
}
