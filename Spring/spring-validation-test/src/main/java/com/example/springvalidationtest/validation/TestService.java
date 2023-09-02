package com.example.springvalidationtest.validation;

import org.springframework.stereotype.Service;

@Service
public class TestService {

    public void testParam(EnumRequestDto dto) {

        Enum.valueOf(Role.class, "ABC");
//
//        TestEntity.builder()
//            .email(dto.getEmail())
//            .age(dto.getAge())
//            .role(Role.valueOf(dto.getRole()))
//            .build();

    }
}
