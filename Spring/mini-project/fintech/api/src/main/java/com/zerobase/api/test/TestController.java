package com.zerobase.api.test;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fintech/api/v1")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/test/get/{userKey}")
    public TestDto.UserInfoDto test(@PathVariable String userKey) {
        return testService.testGetService(userKey);
    }
}
