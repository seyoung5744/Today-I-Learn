package com.example.springbootchatting.stomp.controller;

import com.example.springbootchatting.stomp.dto.ChatDTO;
import com.example.springbootchatting.stomp.repository.MessageRepository;
import com.example.springbootchatting.stomp.service.ChatService;
import com.example.springbootchatting.stomp.type.MessageType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@Controller
@Transactional
@RequiredArgsConstructor
public class StompRabbitController {

    private static final String CHAT_QUEUE_NAME = "chat.queue";
    private static final String CHAT_EXCHANGE_NAME = "chat.exchange";

    private final RabbitTemplate template;
    private final ChatService chatService;
    private final MessageRepository messageRepository;


    @MessageMapping("chat/enterUser")
    public void enterUser(@Payload ChatDTO chat, SimpMessageHeaderAccessor headerAccessor) {
        log.info("유저 {} 입장!!", chat.getSender());

        // 채팅방 유저+1
        chatService.plusUserCnt(chat.getRoomId());

        // 채팅방에 유저 추가 및 UserUUID 반환
        String userUUID = chatService.addUser(chat.getRoomId(), chat.getSender());

        // 반환 결과를 socket session 에 userUUID 로 저장
        headerAccessor.getSessionAttributes().put("userUUID", userUUID);
        headerAccessor.getSessionAttributes().put("roomId", chat.getRoomId());

        chat.setMessage(chat.getSender() + " 님 입장!!");
        chat.setTime(LocalDateTime.now());

//        Message message = chat.toEntity();
//        ChatRoom chatRoom = chatService.findRoomById(chat.getRoomId());
//        message.setChatRoom(chatRoom);
//        messageRepository.save(message);

//        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
        template.convertAndSend(CHAT_EXCHANGE_NAME,  "room." + chat.getRoomId(), chat);

//        List<Message> messageList = messageRepository.findAll(MessageType.TALK, message.getTime());
//
//        for (Message msg : messageList) {
//            template.convertAndSend(CHAT_EXCHANGE_NAME,  "room." + chat.getRoomId(), chat);
//        }
    }

    // 해당 유저
    @MessageMapping("chat/sendMessage")
    public void sendMessage(@Payload ChatDTO chat) {
        log.info("CHAT {}", chat);
        chat.setMessage(chat.getMessage());
        chat.setTime(LocalDateTime.now());

//        Message message = chat.toEntity();
//        ChatRoom chatRoom = chatService.findRoomById(chat.getRoomId());
//        message.setChatRoom(chatRoom);
//        messageRepository.save(message);

        template.convertAndSend(CHAT_EXCHANGE_NAME,  "room." + chat.getRoomId(), chat);
    }

    //receive()는 단순히 큐에 들어온 메세지를 소비만 한다. (현재는 디버그용도)
    @RabbitListener(queues = CHAT_QUEUE_NAME)
    public void receive(ChatDTO chat){
        System.out.println("[x] Received : " + chat.getMessage());
        log.info("[x] Received : {}", chat.getMessage());
    }

    // 유저 퇴장 시에는 EventListener 을 통해서 유저 퇴장을 확인
    @EventListener
    public void webSocketDisconnectListener(SessionDisconnectEvent event) {
        log.info("DisConnEvent {}", event);

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // stomp 세션에 있던 uuid 와 roomId 를 확인해서 채팅방 유저 리스트와 room 에서 해당 유저를 삭제
        String userUUID = (String) headerAccessor.getSessionAttributes().get("userUUID");
        Long roomId = (Long) headerAccessor.getSessionAttributes().get("roomId");

        log.info("headAccessor {}", headerAccessor);

        // 채팅방 유저 -1
        chatService.minusUserCnt(roomId);

        // 채팅방 유저 리스트에서 UUID 유저 닉네임 조회 및 리스트에서 유저 삭제
        String username = chatService.getUserName(roomId, userUUID);
        chatService.delUser(roomId, userUUID);

        if (username != null) {
            log.info("User Disconnected : " + username);

            // builder 어노테이션 활용
            ChatDTO chat = ChatDTO.builder()
                .type(MessageType.LEAVE)
                .sender(username)
                .message(username + " 님 퇴장!!")
                .build();

            template.convertAndSend(CHAT_EXCHANGE_NAME,  "room." + chat.getRoomId(), chat);
        }
    }

    // 채팅에 참여한 유저 리스트 반환
    @GetMapping("/chat/userlist")
    @ResponseBody
    public List<String> userList(Long roomId) {
        return chatService.getUserList(roomId);
    }

    // 채팅에 참여한 유저 닉네임 중복 확인
    @GetMapping("/chat/duplicateName")
    @ResponseBody
    public String isDuplicateName(@RequestParam Long roomId, @RequestParam String username) {

        // 유저 이름 확인
        String userName = chatService.isDuplicateName(roomId, username);
        log.info("닉네임 중복 확인 {}", userName);

        return userName;
    }
}
