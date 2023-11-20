package com.chunjae.pro05.biz;


import com.chunjae.pro05.entity.Board;
import com.chunjae.pro05.util.Page;

import java.util.List;

public interface BoardSerivce {
    public List<Board> boardList();
    public Board boardGet(int no);
    public void boardAdd(Board board);
    public void boardDel(int no);
    public List<Board> boardPage(Page page);
}
