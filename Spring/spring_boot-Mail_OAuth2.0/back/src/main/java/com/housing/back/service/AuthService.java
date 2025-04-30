package com.housing.back.service;

import com.housing.back.dto.request.auth.CheckCertificationRequestDto;
import com.housing.back.dto.request.auth.EmailCertificationRequestDto;
import com.housing.back.dto.request.auth.IdCheckRequestDto;
import com.housing.back.dto.request.auth.SignUpRequestDto;
import com.housing.back.dto.response.auth.CheckCertificationResponseDto;
import com.housing.back.dto.response.auth.EmailCertificationResponseDto;
import com.housing.back.dto.response.auth.IdCheckResponseDto;
import com.housing.back.dto.response.auth.SignUpResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto request);

    ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto request);

    ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto request);

    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto request);


}
