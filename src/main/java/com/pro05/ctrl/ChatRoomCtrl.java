package com.pro05.ctrl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.pro05.entity.Product;
import com.pro05.entity.ProductVO;
import com.pro05.service.ChatService;
import com.pro05.entity.ChatMessage;
import com.pro05.entity.ChatRoom;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pro05.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/chat/")
public class ChatRoomCtrl {
    private static final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private HttpSession session;

    @Autowired
    private ChatService chatService;

    @Autowired
    private ProductService productService;

    // 채팅방 입장
    @GetMapping("roomEnter")
    public String roomEnter(HttpServletRequest request, Model model){
        String sid = (String) session.getAttribute("sid");
        String memId = request.getParameter("memId");
        Long pno = Long.valueOf(request.getParameter("pno"));

        // 없으면 새로 추가, 있으면 가져오기
        ChatRoom room = chatService.chatRoomInsert(memId, pno);
        model.addAttribute("room", room);

        // 기존의 채팅 내역 가져오기
        Long roomNo = room.getRoomNo();
        List<ChatMessage> chats = chatService.chatMessageList(roomNo);
        model.addAttribute("chats", chats);

        // 읽지 않은 첫 채팅부터 시작하기 & 기존 채팅 읽음 처리
        for(ChatMessage c:chats){
            if(c.getStatus().equals("UNREAD")){
                model.addAttribute("firstChat", c);
                break;
            }
        }
        chatService.chatMessageReadUpdates(roomNo, sid);

        // 채팅방 상대 이름 띄우기
        // 채팅방은 구매자 기준으로 저장되므로, 구매자인 경우 product 에서 seller 가져오기
        ProductVO product =  productService.productDetail(pno);
        model.addAttribute("product", product); // 상품 정보
        if(sid.equals(memId)){
            // 구매자인 경우 판매자의 이름
            model.addAttribute("roomName", product.getSeller());
        } else {
            // 판매자인 경우 구매자의 이름
            model.addAttribute("roomName", room.getMemId());
        }



        return "chat/chat";
    }

    @GetMapping("roomList")
    public String roomList(HttpServletRequest request, Model model){
        Long pno = Long.valueOf(request.getParameter("pno"));
        model.addAttribute("pno", pno);

        List<ChatRoom> chatRooms = chatService.chatRoomProductList(pno);
        model.addAttribute("rooms", chatRooms);

        return "chat/chatList";
    }

    @PostMapping("blockRoom")
    @ResponseBody
    public String blockRoom(HttpServletRequest request){
        Long roomNo = Long.valueOf(request.getParameter("pno"));
        int returnNo = chatService.chatRoomBlockUpdate(roomNo);
        if(returnNo>0){
            return "Block Successfully";
        }

        return "Something went wrong";
    }

    @PostMapping("readRoom")
    @ResponseBody
    public String readRoom(HttpServletRequest request){
        Long roomNo = Long.valueOf(request.getParameter("pno"));
        String sender = request.getParameter("memId");

        int returnNo = chatService.chatMessageReadUpdates(roomNo, sender);
        if(returnNo>0){
            return "Success";
        }

        return "Something went wrong";
    }

    @PostMapping("insertChat")
    @ResponseBody
    public ChatMessage insertChat(@RequestParam String message) throws JsonProcessingException {
        ChatMessage chat = mapper.readValue(message, ChatMessage.class);
        
        return chatService.chatMessageInsert(chat);
    }

    @PostMapping("readChat")
    @ResponseBody
    public String readChat(@RequestParam String message, @RequestParam String user) throws JsonProcessingException {
        ChatMessage chat = mapper.readValue(message, ChatMessage.class);

        chatService.chatMessageReadUpdate(chat.getChatNo(), user);

        return "readChat Completed";
    }

    @GetMapping("unreadAll")
    @ResponseBody
    public int unreadAll(@RequestParam String receiver){
        return chatService.chatMessageUnreadAll(receiver);
    }

    @GetMapping("productUpdate")
    @ResponseBody
    public int productUpdate(@RequestParam Long pno, @RequestParam String status) throws JsonProcessingException {
        ProductVO pv = productService.productDetail(pno);
        pv.setStatus(status);

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Product p = mapper.readValue(mapper.writeValueAsString(pv), Product.class);
        Product p = mapper.convertValue(pv, Product.class);


        return productService.productUpdate(p);
    }
}