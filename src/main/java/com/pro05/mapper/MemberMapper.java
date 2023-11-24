package com.pro05.mapper;

import com.pro05.entity.Member;
import com.pro05.util.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MemberMapper {
    // 리스트의 페이지 처리
    @Select({"<script>","SELECT * FROM member WHERE",
            "<if test='searchType != null and searchType != \"\"'> ${searchType} LIKE CONCAT('%', #{searchKeyword}, '%') AND</if>",
            "status!='REMOVE'"+
                    "ORDER BY createAt ASC LIMIT #{postStart}, #{postScreen}","</script>"})
    public List<Member> memberList(Page page);
    // LIMIT (시작 인덱스), (가져올 갯수)
    // LIMIT 는 시작 인덱스 + 1부터 갯수만큼 가져온다.
    // 이때 시작 인덱스는 실제 데이터베이스의 row 기준으로 0부터 시작함. int primary key 와는 관계 없음.
    // 마지막 인덱스가 8이고, 시작인덱스가 6인데 10개를 가져오라고 하면 6, 7, 8만 가져온다.

    @Select("SELECT * FROM member")
    List<Member> memberList2();

    @Select("SELECT Date(createAt) as createAt, COUNT(*) as mno FROM member GROUP BY DATE(createAt) ORDER BY createAt DESC LIMIT 7")
    List<Member> memberCreateStats();

    @Select({"<script>","SELECT COUNT(*) FROM member WHERE",
            "<if test='searchType != null and searchType != \"\"'> ${searchType} LIKE CONCAT('%', #{searchKeyword}, '%') AND</if>",
            "status!='REMOVE'","</script>"})
    public int memberCount(Page page);

    @Select("SELECT * FROM member WHERE id=#{id}")
    Member memberGet(String id);

    @Insert("INSERT INTO member VALUES (default, #{id}, #{pw}, #{name}, #{tel}, #{email}, #{addr1}, #{addr2}, #{addr3}, #{postcode}, default, default)")
    void memberInsert(Member member);

    @Update("UPDATE member SET name=#{name}, tel=#{tel}, email=#{email}, addr1=#{addr1}, addr2=#{addr2}, addr3=#{addr3}, postcode=#{postcode} WHERE id=#{id}")
    void memberUpdate(Member member);

    @Update("UPDATE member SET status='REMOVE' WHERE id=#{id}")
    void memberRemoveUpdate(String id);

    @Delete("DELETE FROM member WHERE id=#{id}")
    void memberDelete(String id);

    @Select("SELECT COUNT(*) FROM member WHERE id = #{id}")
    int idCheck (String id);

    @Update("UPDATE member SET status=default WHERE id=#{id}")
    void memberactive(String id);

    @Update("UPDATE member SET status='OUTSIDE' WHERE id=#{id}")
    void memberOutside(String id);

    @Update("UPDATE member SET status='REST' WHERE id=#{id}")
    void change(String id);

    @Update("UPDATE member SET loginAt=default WHERE id=#{id}")
    void loginAt(String id);

    @Update("UPDATE member SET pw=#{pw} WHERE id=#{id}")
    void changePw(Member member);
}
