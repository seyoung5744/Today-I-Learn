package com.example.springvalidationtest.validation;

import com.example.springvalidationtest.validation.validator.CustomEnumDeserializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
@JsonDeserialize(using = CustomEnumDeserializer.class)
public enum Role {
    ADMIN, USER;

//    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
//    public static Role findByCode(String code) {
//        return Stream.of(Role.values())
//            .filter(c -> c.name().equals(code))
//            .findFirst()
//            .orElse(null);
//    }

}
