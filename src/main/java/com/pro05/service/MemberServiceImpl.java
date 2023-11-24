package com.pro05.service;

import com.pro05.mapper.MemberMapper;
import com.pro05.entity.Member;
import com.pro05.util.Page;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberMapper memberMapper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public List<Member> memberList(Page page) {
        return memberMapper.memberList(page);
    }
    @Override
    public int memberCount(Page page) {
        return memberMapper.memberCount(page);
    }

    @Override
    public List<Member> memberList2() {
        return memberMapper.memberList2();
    }

//
//    public void checkloginAt() {
//            List<Member> acc= memberMapper.memberList2();
//            logger.info("답 ㅡㅡㅡㅡ" + acc);
//            Timestamp date1 = Timestamp.valueOf(LocalDateTime.now().minusDays(30));
//            for (Member mem : acc) {
//                if (date1.after(mem.getLoginAt())) {
//                mem.setStatus("REST");
//            }
//        }
//    }

    @Override
    public Member memberGet(String id) {
        return memberMapper.memberGet(id);
    }

    @Override
    public void memberInsert(Member member) {
        String ppw = pwEncoder.encode(member.getPw());
        member.setPw(ppw);
         memberMapper.memberInsert(member);
    }

    @Override
    public void memberUpdate(Member member) {
         memberMapper.memberUpdate(member);
    }

    @Override
    public void memberRemoveUpdate(String id) {
         memberMapper.memberRemoveUpdate(id);
    }

    @Override
    public void memberDelete(String id) {
         memberMapper.memberDelete(id);
    }

    @Override
    public boolean idCheck(String id) {
        boolean pass = true;
        int cnt = memberMapper.idCheck(id);
        if(cnt > 0) pass = false;
        return pass;
    }

    @Override
    public int loginPro(String id, String pw) {
        int pass = 0;
        Member mem = memberMapper.memberGet(id);
//        Timestamp date1 = Timestamp.valueOf(LocalDateTime.now().minusDays(30));
//        if (mem != null && date1.after(mem.getLoginAt())) {
//            memberMapper.change(id);
//            pass = 2;
//        } else {
            if (mem != null && pwEncoder.matches(pw, mem.getPw())) {
                if (mem.getStatus().equals("ACTIVE")) {
                    memberMapper.loginAt(id);
                    pass = 1;
                } else if (mem.getStatus().equals("REST")) {
                    pass = 2;
                } else if (mem.getStatus().equals("OUTSIDE")) {
                    pass = 3;
                }
            } else {
                pass = 0;
            }

        return pass;
    }

    @Override
    public void memberactive(String id) {
        memberMapper.memberactive(id);
    }

    @Override
    public void memberOutside(String id) {
        memberMapper.memberOutside(id);
    }

    @Override
    public void change(String id, LocalDateTime createAt) {
        memberMapper.change(id);
    }

    @Override
    public void loginAt(String id) {
        memberMapper.loginAt(id);
    }

    @Override
    public List<Member> memberCreateStats() {
        return memberMapper.memberCreateStats();
    }

    @Override
    public void changePw(Member member) {
        String ppw = pwEncoder.encode(member.getPw());
        member.setPw(ppw);
        memberMapper.changePw(member);
    }
}

