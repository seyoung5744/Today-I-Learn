package com.spartaboys.rebasetest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test2Controller {

    @GetMapping("test2")
    public String test2() {
        return "test2";
    }
}
