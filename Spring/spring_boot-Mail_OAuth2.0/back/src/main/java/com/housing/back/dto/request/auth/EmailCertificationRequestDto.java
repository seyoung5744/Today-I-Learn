package com.housing.back.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EmailCertificationRequestDto {

    @NotBlank
    private String id;

    @NotBlank
    private String email;
}
