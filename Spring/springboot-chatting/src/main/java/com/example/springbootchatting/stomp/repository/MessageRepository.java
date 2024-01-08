package com.example.springbootchatting.stomp.repository;

import com.example.springbootchatting.stomp.domain.Message;
import com.example.springbootchatting.stomp.type.MessageType;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("select m from Message m where m.messageType =: messageType and m.time >=: time")
    List<Message> findAll(MessageType messageType, LocalDateTime time);
}
