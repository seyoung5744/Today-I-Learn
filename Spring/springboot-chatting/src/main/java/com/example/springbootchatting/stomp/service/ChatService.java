package com.example.springbootchatting.stomp.service;

import com.example.springbootchatting.stomp.domain.ChatRoom;
import com.example.springbootchatting.stomp.dto.ChatRoomDTO;
import com.example.springbootchatting.stomp.repository.ChatRoomRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final FileService fileService;

    // 전체 채팅방 조회
    public List<ChatRoom> findAllRoom() {
        // 채팅방 생성 순서를 최근순으로 반환
        return chatRoomRepository.findAll();
    }

    public ChatRoom findRoomById(Long roomId) {
        return chatRoomRepository.findById(roomId).get();
    }

    public ChatRoom createChatRoom(String roomName, String roomPwd, boolean secretChk, int maxUserCnt) {

        ChatRoom chatRoom = ChatRoom.builder()
            .roomName(roomName)
            .roomPwd(roomPwd) // 채팅방 패스워드
            .secretChk(secretChk) // 채팅방 잠금 여부
            .userCount(0) // 채팅방 참여 인원수
            .maxUserCnt(maxUserCnt) // 최대 인원수 제한
            .build();

        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

    // 채팅방 인원+1
    public void plusUserCnt(Long roomId) {
        ChatRoom room = chatRoomRepository.findById(roomId).get();
        room.setUserCount(room.getUserCount() + 1);
    }

    // 채팅방 인원-1
    public void minusUserCnt(Long roomId) {
        ChatRoom room = chatRoomRepository.findById(roomId).get();
        room.setUserCount(room.getUserCount() - 1);
    }

    // maxUserCnt 에 따른 채팅방 입장 여부
    public boolean chkRoomUserCnt(Long roomId) {
        ChatRoom room = chatRoomRepository.findById(roomId).get();

        log.info("참여인원 확인 [{}, {}]", room.getUserCount(), room.getMaxUserCnt());

        return room.getUserCount() + 1 <= room.getMaxUserCnt();
    }

    // 채팅방 유저 리스트에 유저 추가
    public String addUser(Long roomId, String userName) {
        ChatRoom room = chatRoomRepository.findById(roomId).get();
        String userUUID = UUID.randomUUID().toString();

        // 아이디 중복 확인 후 userList 에 추가
        Map<String, String> userList = room.getUserList();
        userList.put(userUUID, userName);

        room.setUserList(userList);
        return userUUID;
    }

    // 채팅방 유저 이름 중복 확인
    public String isDuplicateName(Long roomId, String username) {
        ChatRoom room = chatRoomRepository.findById(roomId).get();
        String tmp = username;

        // 만약 userName 이 중복이라면 랜덤한 숫자를 붙임
        // 이때 랜덤한 숫자를 붙였을 때 getUserlist 안에 있는 닉네임이라면 다시 랜덤한 숫자 붙이기!
        while (room.getUserList().containsValue(tmp)) {
            int ranNum = (int) (Math.random() * 100) + 1;

            tmp = username + ranNum;
        }

        return tmp;
    }

    // 채팅방 유저 리스트 삭제
    public void delUser(Long roomId, String userUUID) {
        ChatRoom room = chatRoomRepository.findById(roomId).get();
        Map<String, String> userList = room.getUserList();
        userList.remove(userUUID);

        room.setUserList(userList);
    }

    // 채팅방 userName 조회
    public String getUserName(Long roomId, String userUUID) {
        ChatRoom room = chatRoomRepository.findById(roomId).get();
        return room.getUserList().get(userUUID);
    }

    // 채팅방 전체 userlist 조회
    public ArrayList<String> getUserList(Long roomId) {
        ArrayList<String> list = new ArrayList<>();

        ChatRoom room = chatRoomRepository.findById(roomId).get();

        // hashmap 을 for 문을 돌린 후
        // value 값만 뽑아내서 list 에 저장 후 reutrn
        room.getUserList().forEach((key, value) -> list.add(value));
        return list;
    }

    // 채팅방 비밀번호 조회
    public boolean confirmPwd(Long roomId, String roomPwd) {
        ChatRoom room = chatRoomRepository.findById(roomId).get();
        return roomPwd.equals(room.getRoomPwd());
    }

    // 채팅방 삭제
    public void delChatRoom(Long roomId) {
        try {
            // 채팅방 삭제
            ChatRoom room = chatRoomRepository.findById(roomId).get();
            chatRoomRepository.delete(room);

            // 채팅방 안에 있는 파일 삭제
            fileService.deleteFileDir(String.valueOf(roomId));

            log.info("삭제 완료 roomId : {}", roomId);

        } catch (Exception e) { // 만약에 예외 발생시 확인하기 위해서 try catch
            System.out.println(e.getMessage());
        }
    }
}
