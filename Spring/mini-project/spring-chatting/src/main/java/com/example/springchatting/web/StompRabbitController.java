package com.example.springchatting.web;

import com.example.springchatting.dto.ChatMessageDTO;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompRabbitController {

    private final RabbitTemplate template;

    private final static String CHAT_QUEUE_NAME = "chat.queue";
    private final static String CHAT_EXCHANGE_NAME = "chat.exchange";

    @MessageMapping("chat.enter.{chatRoomId}")
    public void enter(ChatMessageDTO chat, @DestinationVariable String chatRoomId) {

        chat.setMessage("입장하셨습니다.");
        chat.setRegDate(LocalDateTime.now());

//        template.convertAndSend(CHAT_EXCHANGE_NAME, "room." + chatRoomId, chat); // exchange
//        template.convertAndSend("room." + chatRoomId, chat); //queue
        template.convertAndSend("amq.topic", "room." + chatRoomId, chat); //topic
    }

    @MessageMapping("chat.message.{chatRoomId}")
    public void send(ChatMessageDTO chat, @DestinationVariable String chatRoomId) {

        chat.setRegDate(LocalDateTime.now());

//        template.convertAndSend(CHAT_EXCHANGE_NAME, "room." + chatRoomId, chat);
//        template.convertAndSend( "room." + chatRoomId, chat);
        template.convertAndSend("amq.topic", "room." + chatRoomId, chat);
    }

    //receive()는 단순히 큐에 들어온 메세지를 소비만 한다. (현재는 디버그용도)
    @RabbitListener(queues = CHAT_QUEUE_NAME)
    public void receive(ChatMessageDTO chat) {
        System.out.println("received : " + chat.getMessage());
    }
}
