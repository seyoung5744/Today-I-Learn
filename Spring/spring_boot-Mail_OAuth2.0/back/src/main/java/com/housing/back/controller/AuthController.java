package com.housing.back.controller;

import com.housing.back.dto.request.auth.CheckCertificationRequestDto;
import com.housing.back.dto.request.auth.EmailCertificationRequestDto;
import com.housing.back.dto.request.auth.IdCheckRequestDto;
import com.housing.back.dto.response.auth.CheckCertificationResponseDto;
import com.housing.back.dto.response.auth.EmailCertificationResponseDto;
import com.housing.back.dto.response.auth.IdCheckResponseDto;
import com.housing.back.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/id-check")
    public ResponseEntity<? super IdCheckResponseDto> idCheck(@Valid @RequestBody IdCheckRequestDto request) {
        return authService.idCheck(request);
    }

    @PostMapping("/email-certification")
    public ResponseEntity<? super EmailCertificationResponseDto> emailCertification(@Valid @RequestBody EmailCertificationRequestDto request) {
        return authService.emailCertification(request);
    }

    @PostMapping("/check-certification")
    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification(@Valid @RequestBody CheckCertificationRequestDto request) {
        return authService.checkCertification(request);
    }
}
