package com.housing.back.controller;

import com.housing.back.dto.request.auth.*;
import com.housing.back.dto.response.auth.*;
import com.housing.back.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/sign-up")
    public ResponseEntity<? super SignUpResponseDto> signUp(@Valid @RequestBody SignUpRequestDto request) {
        return authService.signUp(request);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInResponseDto> signIn(@Valid @RequestBody SignInRequestDto request) {
        return authService.signIn(request);
    }
}
