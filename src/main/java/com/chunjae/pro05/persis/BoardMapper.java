package com.chunjae.pro05.persis;


import com.chunjae.pro05.entity.Board;
import com.chunjae.pro05.util.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Mapper
public interface BoardMapper {
    @Select("select * from board")
    public List<Board> boardList();

    @Select("select * from board where no=#{no}")
    public Board boardGet(int no);

    @Insert("insert into board values(default, #{title}, #{content}, #{author}, #{bfile}, default)")
    public void boardAdd(Board board);

    @Delete("delete from board where no=#{no}")
    public void boardDel(int no);

    @Select({"<script>","SELECT * FROM board" +
            "<if test='searchType != null and searchType != \"\"'> WHERE ${searchType} LIKE CONCAT('%', #{searchKeyword}, '%')" +
            "</if>" +
            " ORDER BY resdate ASC LIMIT #{postStart}, #{postCount}", "</script>"})
    public List<Board> boardPage(Page page);
}
