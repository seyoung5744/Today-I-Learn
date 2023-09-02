package com.example.springvalidationtest.validation;

import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @PostMapping("/test")
    public String test(@Valid @RequestBody EnumRequestDto request){
        log.info("test param : {}", request);
        return "OK";
    }
}
