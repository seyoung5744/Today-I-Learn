package com.example.springbootchatting.stomp.repository;

import com.example.springbootchatting.stomp.dto.ChatRoomDTO;
import com.example.springbootchatting.stomp.service.FileService;
import java.util.*;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

// 추후 DB 와 연결 시 Service 와 Repository(DAO) 로 분리 예정
@Slf4j
@Repository
@RequiredArgsConstructor
public class ChatRepository {

    private Map<String, ChatRoomDTO> chatRoomMap;

    private final FileService fileService;

    @PostConstruct
    private void init() {
        chatRoomMap = new LinkedHashMap<>();
    }

    // 전체 채팅방 조회
    public List<ChatRoomDTO> findAllRoom() {
        // 채팅방 생성 순서를 최근순으로 반환
        List<ChatRoomDTO> chatRoomDTOS = new ArrayList<>(chatRoomMap.values());
        Collections.reverse(chatRoomDTOS);
        return chatRoomDTOS;
    }

    // roomID 기준으로 채팅방 찾기
    public ChatRoomDTO findRoomById(String roomId) {
        return chatRoomMap.get(roomId);
    }

    // roomName 로 채팅방 만들기
    public ChatRoomDTO createChatRoom(String roomName, String roomPwd, boolean secretChk, int maxUserCnt) {
        // roomName 와 roomPwd 로 chatRoom 빌드 후 return

        ChatRoomDTO chatRoomDTO = ChatRoomDTO.builder()
            .roomId(UUID.randomUUID().toString())
            .roomName(roomName)
            .roomPwd(roomPwd) // 채팅방 패스워드
            .secretChk(secretChk) // 채팅방 잠금 여부
            .userList(new HashMap<String, String>())
            .userCount(0) // 채팅방 참여 인원수
            .maxUserCnt(maxUserCnt) // 최대 인원수 제한
            .build();

        // map 에 채팅룸 아이디와 만들어진 채팅룸을 저장장
        chatRoomMap.put(chatRoomDTO.getRoomId(), chatRoomDTO);

        return chatRoomDTO;
    }

    // 채팅방 인원+1
    public void plusUserCnt(String roomId) {
        ChatRoomDTO room = chatRoomMap.get(roomId);
        room.setUserCount(room.getUserCount() + 1);
    }

    // 채팅방 인원-1
    public void minusUserCnt(String roomId) {
        ChatRoomDTO room = chatRoomMap.get(roomId);
        room.setUserCount(room.getUserCount() - 1);
    }

    // maxUserCnt 에 따른 채팅방 입장 여부
    public boolean chkRoomUserCnt(String roomId){
        ChatRoomDTO room = chatRoomMap.get(roomId);

        log.info("참여인원 확인 [{}, {}]", room.getUserCount(), room.getMaxUserCnt());

        return room.getUserCount() + 1 <= room.getMaxUserCnt();
    }

    // 채팅방 유저 리스트에 유저 추가
    public String addUser(String roomId, String userName) {
        ChatRoomDTO room = chatRoomMap.get(roomId);
        String userUUID = UUID.randomUUID().toString();

        // 아이디 중복 확인 후 userList 에 추가
        room.getUserList().put(userUUID, userName);

        return userUUID;
    }

    // 채팅방 유저 이름 중복 확인
    public String isDuplicateName(String roomId, String username) {
        ChatRoomDTO room = chatRoomMap.get(roomId);
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
    public void delUser(String roomId, String userUUID) {
        ChatRoomDTO room = chatRoomMap.get(roomId);
        room.getUserList().remove(userUUID);
    }

    // 채팅방 userName 조회
    public String getUserName(String roomId, String userUUID) {
        ChatRoomDTO room = chatRoomMap.get(roomId);
        return room.getUserList().get(userUUID);
    }

    // 채팅방 전체 userlist 조회
    public ArrayList<String> getUserList(String roomId) {
        ArrayList<String> list = new ArrayList<>();

        ChatRoomDTO room = chatRoomMap.get(roomId);

        // hashmap 을 for 문을 돌린 후
        // value 값만 뽑아내서 list 에 저장 후 reutrn
        room.getUserList().forEach((key, value) -> list.add(value));
        return list;
    }

    // 채팅방 비밀번호 조회
    public boolean confirmPwd(String roomId, String roomPwd) {
        return roomPwd.equals(chatRoomMap.get(roomId).getRoomPwd());
    }

    // 채팅방 삭제
    public void delChatRoom(String roomId) {
        try {
            // 채팅방 삭제
            chatRoomMap.remove(roomId);

            // 채팅방 안에 있는 파일 삭제
            fileService.deleteFileDir(roomId);

            log.info("삭제 완료 roomId : {}", roomId);

        } catch (Exception e) { // 만약에 예외 발생시 확인하기 위해서 try catch
            System.out.println(e.getMessage());
        }
    }
}
