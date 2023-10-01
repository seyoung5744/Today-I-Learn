package com.example.springbootfcm.firebase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FCMController {

    private final FCMService fcmService;

    @GetMapping("/v1")
    public String v1() {
        return "test";
    }

    @PostMapping("/message")
    public String sendMessage(@RequestBody FcmTokenRequest request) {
        log.info("토큰 {}", request.getToken());
        fcmService.sandMessage(request.getToken());
        return "test";
    }
}
