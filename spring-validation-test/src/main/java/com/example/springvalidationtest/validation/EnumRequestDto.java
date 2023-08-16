package com.example.springvalidationtest.validation;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EnumRequestDto {

    @Email(message = "이메일은 필수 입력입니다.")
    private String email;

    @NotNull(message = "나이는 null 일 수 없습니다.")
    private Integer age;

    @EnumPattern(regexp = "USER|ADMIN")
    private Role role;
}
