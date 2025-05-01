package com.housing.back.service;

import com.housing.back.dto.request.auth.*;
import com.housing.back.dto.response.auth.*;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto request);

    ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto request);

    ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto request);

    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto request);

    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto request);

}
