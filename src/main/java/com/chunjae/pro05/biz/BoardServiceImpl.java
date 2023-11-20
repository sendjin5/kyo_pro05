package com.chunjae.pro05.biz;

import com.chunjae.pro05.entity.Board;
import com.chunjae.pro05.persis.BoardMapper;
import com.chunjae.pro05.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardSerivce{

    @Autowired
    private BoardMapper boardMapper;

    @Override
    public List<Board> boardList() {
        return boardMapper.boardList();
    }

    @Override
    public Board boardGet(int no) {
        return boardMapper.boardGet(no);
    }

    @Override
    public void boardAdd(Board board) {

        boardMapper.boardAdd(board);
    }

    @Override
    public void boardDel(int no) {
        boardMapper.boardDel(no);
    }

    @Override
    public List<Board> boardPage(Page page) {
        return boardMapper.boardPage(page);
    }
}
