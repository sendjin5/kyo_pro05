package com.pro05.ctrl;

import com.pro05.service.ChatService;
import com.pro05.entity.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@ServerEndpoint(value = "/socket")
public class ChatCtrl {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private ChatService service;
    private static final List<Session> sessionList = new ArrayList<Session>();

    @OnOpen  // socket 연결 시
    public void onOpen(Session session) {
        sessionList.add(session);
    }

    @OnMessage
    public void onMessage (String message, Session session) throws IOException {
        // 다른 사람에게 메세지 보내기
        Map<String, List<String>> requestParameter = session.getRequestParameterMap();
        Long roomNo = Long.valueOf(requestParameter.get("roomNo").get(0));

        ChatMessage chat = mapper.readValue(message, ChatMessage.class);
        sendRoomMessage(message, roomNo);

        /*ChatMessage chatReturn = service.chatMessageInsert(chat);

        if(chatReturn!=null) {
            sendRoomMessage(message, roomNo, chatReturn);
        } else {
            chat.setType(ChatMessage.MessageType.NOTICE);
            chat.setSender("admin");
            chat.setMessage("대화 상대에게 차단되어 메시지를 보낼 수 없어요.");
            sendRoomMessage(message, roomNo, chatReturn);
        }*/
    }

    @OnError
    public void onError(Throwable e, Session session) {
        System.out.println(e.getMessage() + "by session : " + session.getId());
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        sessionList.remove(session);
    }

    private void sendAllSessionToMessage(String msg){ // 연결된 모든 사용자에게 메세지 전달
        try {
            for(Session s : ChatCtrl.sessionList){
                s.getBasicRemote().sendText(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendRoomMessage(String msg, Long roomNo){
        try {
            for(Session s : ChatCtrl.sessionList){
                Map<String, List<String>> requetParameter = s.getRequestParameterMap();
                int sroomNo = Integer.parseInt(requetParameter.get("roomNo").get(0));
                if(sroomNo == roomNo){
                    s.getBasicRemote().sendText(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}