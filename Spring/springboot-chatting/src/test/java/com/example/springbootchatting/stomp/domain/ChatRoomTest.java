package com.example.springbootchatting.stomp.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class ChatRoomTest {

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    @Rollback(value = false)
    public void save() {
        //given

        Map<String, String> map = new HashMap<>();
        map.put("UUID1", "test1");
        map.put("UUID2", "test2");

        ChatRoom chatRoom = ChatRoom.builder()
            .roomId("1234")
            .roomName("Test")
            .userCount(10)
            .maxUserCnt(100)
            .roomPwd("123")
            .secretChk(false)
            .userList(map)
            .build();
        em.persist(chatRoom);
    }
}