package com.pro05.mapper;

import com.pro05.entity.ChatMessage;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ChatMessageMapper {
    // 과거 보낸 채팅부터 정렬
    @Select("SELECT * FROM chatMessage WHERE roomNo=#{roomNo} AND status!='REMOVE' ORDER BY time ASC")
    public List<ChatMessage> chatMessageList(Long roomNo);

    @Select("SELECT COUNT(*) FROM chatMessage WHERE roomNo=#{roomNo} AND status='UNREAD'")
    public int chatMessageUnread(Long roomNo);

    @Select("SELECT COUNT(*) FROM chatMessage WHERE receiver=#{receiver} AND status='UNREAD'")
    public int chatMessageUnreadAll(String receiver);

    @Select("SELECT * FROM chatMessage ORDER BY chatNo DESC LIMIT 1")
    public ChatMessage chatMessageGetLast();

    @Insert("INSERT INTO chatMessage(type, roomNo, sender, receiver, message) VALUES(#{type}, #{roomNo}, #{sender}, #{receiver}, #{message})")
    public int chatMessageInsert(ChatMessage chatMessage);

    @Update("UPDATE chatMessage SET status='READ' WHERE chatNo=#{chatNo} AND sender!=#{sender}")
    public int chatMessageReadUpdate(Long chatNo, String sender);

    // 상대방이 보낸 메시지만 읽음 처리
    @Update("UPDATE chatMessage SET status='READ' WHERE roomNo=#{roomNo} AND sender!=#{sender}")
    public int chatMessageReadUpdates(Long roomNo, String sender);

    @Update("UPDATE chatMessage SET status='REMOVE' WHERE chatNo=#{chatNo}")
    public int chatMessageRemoveUpdate(Long chatNo);

    @Delete("DELETE FROM chatMessage WHERE chatNo=#{chatNo}")
    public int chatMessageDelete(Long chatNo);
}
