package com.example.springbootchatting.stomp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class LoginController {

    @GetMapping("/chatlogin")
    public String goLogin(){
        return "/chatlogin";
    }

    @PostMapping("/chatlogin")
    public String login(@RequestParam String username, @RequestParam String pass, RedirectAttributes rttr) {
        log.info("username = {}, password = {}", username, pass);
        rttr.addFlashAttribute("user", username);
        return "redirect:/";
    }
}
