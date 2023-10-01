package com.example.springbootfcm.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FCMService implements MessageService{

    @Override
    public void sandMessage(String token) {
        Message message = Message.builder()
            .putData("title", "테스트 제목입니다.")
            .putData("content", "테스트 내용입니다.")
            .setToken(token)
            .build();

        send(message);
    }

    private void send(Message message) {
        log.info("메세지 비동기 전송");
        try {
            String send = FirebaseMessaging.getInstance().send(message);
            log.info("전송 성공 {}", send);
        }catch (FirebaseMessagingException e){
            log.warn(e.getMessage());
        }
    }
}
