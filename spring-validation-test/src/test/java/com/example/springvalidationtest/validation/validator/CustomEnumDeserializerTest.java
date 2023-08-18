package com.example.springvalidationtest.validation.validator;

import static org.junit.jupiter.api.Assertions.*;

import com.example.springvalidationtest.validation.Role;
import org.junit.jupiter.api.Test;

class CustomEnumDeserializerTest {

    @Test
    public void enumValueOfTest () {
        System.out.println(Enum.valueOf(Role.class, "ABC"));
        System.out.println(Enum.valueOf(Role.class, "USER"));

    }

}