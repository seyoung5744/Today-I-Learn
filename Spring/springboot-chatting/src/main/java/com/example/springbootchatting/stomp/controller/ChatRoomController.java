package com.example.springbootchatting.stomp.controller;

import com.example.springbootchatting.stomp.domain.ChatRoom;
import com.example.springbootchatting.stomp.service.ChatService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatService chatService;

    // 채팅 리스트 화면
    // / 로 요청이 들어오면 전체 채팅룸 리스트를 담아서 return
    @GetMapping("/")
    public String goChatRoom(Model model){
        List<ChatRoom> allRoom = chatService.findAllRoom();
        model.addAttribute("list", allRoom);
        log.info("SHOW ALL ChatList {}", allRoom);
        return "roomlist";
    }

    // 채팅방 생성
    // 채팅방 생성 후 다시 / 로 return
    @PostMapping("/chat/createroom")
    public String createRoom(@RequestParam String roomName, @RequestParam String roomPwd, @RequestParam Boolean secretChk,
        @RequestParam(defaultValue = "100") Integer maxUserCnt , RedirectAttributes rttr) {

        ChatRoom room = chatService.createChatRoom(roomName, roomPwd, secretChk, maxUserCnt);

        log.info("CREATE Chat Room {}", room);

        rttr.addFlashAttribute("roomName", room);
        return "redirect:/";
    }

    // 채팅방 입장 화면
    // 파라미터로 넘어오는 roomId 를 확인후 해당 roomId 를 기준으로
    // 채팅방을 찾아서 클라이언트를 chatroom 으로 보낸다.
    @GetMapping("/chat/room")
    public String roomDetail(Model model, @RequestParam Long roomId){

        log.info("roomId {}", roomId);
        model.addAttribute("room", chatService.findRoomById(roomId));
        return "chatroom";
    }

    // 채팅방 비밀번호 확인
    @PostMapping("/chat/confirmPwd/{roomId}")
    @ResponseBody
    public boolean confirmPwd(@PathVariable Long roomId, @RequestParam String roomPwd){

        // 넘어온 roomId 와 roomPwd 를 이용해서 비밀번호 찾기
        // 찾아서 입력받은 roomPwd 와 room pwd 와 비교해서 맞으면 true, 아니면  false
        return chatService.confirmPwd(roomId, roomPwd);
    }

    // 채팅방 삭제
    @GetMapping("/chat/delRoom/{roomId}")
    public String delChatRoom(@PathVariable Long roomId){

        // roomId 기준으로 chatRoomMap 에서 삭제, 해당 채팅룸 안에 있는 사진 삭제
        chatService.delChatRoom(roomId);

        return "redirect:/";
    }

    @GetMapping("/chat/chkUserCnt/{roomId}")
    @ResponseBody
    public boolean chUserCnt(@PathVariable Long roomId){
        return chatService.chkRoomUserCnt(roomId);
    }
}
