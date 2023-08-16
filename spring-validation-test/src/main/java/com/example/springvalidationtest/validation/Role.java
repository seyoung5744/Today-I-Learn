package com.example.springvalidationtest.validation;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum Role {
    ADMIN, USER;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Role findByCode(String code) {
        System.out.println(code);
        System.out.println(Role.values()[0].name());
        return Stream.of(Role.values())
            .filter(c -> c.name().equals(code))
            .findFirst()
            .orElse(null);
    }

}
