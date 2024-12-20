package com.example.springchatting.web;


import com.example.springchatting.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

//    private final ChatRoomRepository repository;
//
//    //채팅방 목록 조회
//    @GetMapping("/rooms")
//    public ModelAndView rooms() {
//
//        log.info("# All Chat Rooms");
//        ModelAndView mv = new ModelAndView("chat/rooms");
//
//        mv.addObject("list", repository.findAllRooms());
//
//        return mv;
//    }
//
//    //채팅방 개설
//    @PostMapping("/room")
//    public String create(@RequestParam String name, RedirectAttributes rttr) {
//
//        log.info("# Create Chat Room , name: " + name);
//        rttr.addFlashAttribute("roomName", repository.createChatRoomDTO(name));
//        return "redirect:/chat/rooms";
//    }
//
//    //채팅방 조회
//    @GetMapping("/room")
//    public void getRoom(String roomId, Model model) {
//
//        log.info("# get Chat Room, roomID : " + roomId);
//
//        model.addAttribute("room", repository.findRoomById(roomId));
//    }

    @GetMapping("/rooms")
    public String getRooms(){
        return "chat/rooms";
    }

    @GetMapping(value = "/room")
    public String getRoom(@RequestParam Long chatRoomId, @RequestParam String nickname, Model model){

        model.addAttribute("chatRoomId", chatRoomId);
        model.addAttribute("nickname", nickname);
        log.info("{} : {}", chatRoomId, nickname);
        return "chat/room";
    }
}
