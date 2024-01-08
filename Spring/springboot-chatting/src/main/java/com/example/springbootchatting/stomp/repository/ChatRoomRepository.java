package com.example.springbootchatting.stomp.repository;

import com.example.springbootchatting.stomp.domain.ChatRoom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("select cr from ChatRoom cr order by cr.roomId desc")
    List<ChatRoom> findAll();
}
