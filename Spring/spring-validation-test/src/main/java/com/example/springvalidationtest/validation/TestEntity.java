package com.example.springvalidationtest.validation;

import lombok.Builder;
import lombok.ToString;


@Builder
@ToString
public class TestEntity {

    private String email;

    private Integer age;

    private Role role;
}
