package com.pro05.service;

import com.pro05.entity.ChatMessage;
import com.pro05.entity.ChatRoom;
import com.pro05.mapper.ChatMessageMapper;
import com.pro05.mapper.ChatRoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    ChatRoomMapper roomMapper;
    @Autowired
    ChatMessageMapper chatMapper;

    @Override
    public List<ChatRoom> chatRoomProductList(Long pno) {
        return roomMapper.chatRoomProductList(pno);
    }

    @Override
    public ChatRoom chatRoomGetNo(Long roomNo) {
        return roomMapper.chatRoomGet(roomNo);
    }

    @Override
    public ChatRoom chatRoomInsert(String memId, Long pno) {
        if(roomMapper.chatRoomGetUnique(memId, pno)<1){
            roomMapper.chatRoomInsert(memId, pno);
        }

        return roomMapper.chatRoomGetId(pno, memId);
    }

    @Override
    public int chatRoomBlockUpdate(Long roomNo) {
        return roomMapper.chatRoomBlockUpdate(roomNo);
    }

    @Override
    public List<ChatMessage> chatMessageList(Long roomNo) {
        return chatMapper.chatMessageList(roomNo);
    }

    @Override
    public ChatMessage chatMessageInsert(ChatMessage chatMessage) {
        Long roomNo = chatMessage.getRoomNo();
        ChatRoom room = roomMapper.chatRoomGet(roomNo);
        if(room.getStatus().equals("BLOCK")){
            return null; // 차단된 경우에는 메시지 전송하지 않음.
        }
        chatMapper.chatMessageInsert(chatMessage);
        return chatMapper.chatMessageGetLast();
    }

    @Override
    public int chatMessageReadUpdate(Long chatNo, String sender) {
        return chatMapper.chatMessageReadUpdate(chatNo, sender);
    }

    @Override
    public int chatMessageReadUpdates(Long roomNo, String sender) {
        return chatMapper.chatMessageReadUpdates(roomNo, sender);
    }

    @Override
    public int chatMessageRemoveUpdate(Long chatNo) {
        return chatMapper.chatMessageRemoveUpdate(chatNo);
    }

    @Override
    public int chatMessageUnreadAll(String receiver) {
        return chatMapper.chatMessageUnreadAll(receiver);
    }

    @Override
    public int chatMessageUnread(Long roomNo){
        return chatMapper.chatMessageUnread(roomNo);
    }

    @Override
    public List<ChatRoom> chatRoomMy(String id) {
        return roomMapper.chatRoomMy(id);
    }
}
