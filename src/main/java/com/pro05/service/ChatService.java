package com.pro05.service;

import com.pro05.entity.ChatMessage;
import com.pro05.entity.ChatRoom;

import java.util.List;

public interface ChatService {
    public List<ChatRoom> chatRoomProductList(Long pno);
    public ChatRoom chatRoomGetNo(Long roomNo);
    public ChatRoom chatRoomInsert(String memId, Long pno);
    public int chatRoomBlockUpdate(Long roomNo);

    public List<ChatMessage> chatMessageList(Long roomNo);
    public ChatMessage chatMessageInsert(ChatMessage chatMessage);
    public int chatMessageReadUpdate(Long chatNo, String sender);
    public int chatMessageReadUpdates(Long roomNo, String sender);
    public int chatMessageRemoveUpdate(Long chatNo);

    public int chatMessageUnreadAll(String receiver);
    public int chatMessageUnread(Long roomNo);
    public List<ChatRoom> chatRoomMy(String id);
}
