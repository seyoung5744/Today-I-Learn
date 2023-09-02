package com.example.springoauthlogin.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
// http://localhost:8080/login/oauth2/code/google
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/login/oauth2")
public class OauthController {

    private final GoogleClient googleClient;

    @GetMapping("/code/google")
    public void successGoogleLogin(@RequestParam("code") String accessCode) {
        log.info(accessCode);
//        log.info(googleClient.requestAccessToken(accessCode).toString());
    }

    @PostMapping("/login")
    public void login(@RequestParam("code") String code) {
        log.info(code);
        log.info(googleClient.requestAccessToken(code).toString());
    }

}
