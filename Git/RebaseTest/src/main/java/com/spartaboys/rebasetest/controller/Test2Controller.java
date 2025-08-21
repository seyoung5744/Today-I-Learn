package com.spartaboys.rebasetest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test2Controller {

    @GetMapping("/test2")
    public String test2() {
        return "Test2 컨트롤러입니다";
    }
}
