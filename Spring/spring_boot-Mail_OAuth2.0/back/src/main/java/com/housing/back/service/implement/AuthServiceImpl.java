package com.housing.back.service.implement;

import com.housing.back.dto.request.auth.IdCheckRequestDto;
import com.housing.back.dto.response.ResponseDto;
import com.housing.back.dto.response.auth.IdCheckResponseDto;
import com.housing.back.repository.UserRepository;
import com.housing.back.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto request) {

        try {
            String userId = request.getId();
            boolean isExisted = userRepository.existsByUserId(userId);
            if (isExisted) {
                return IdCheckResponseDto.duplicateId();
            }
        } catch (Exception e) {
            log.error("Exception [Err_Msg]: {}", e.getMessage());
            return ResponseDto.databaseError();
        }
        return IdCheckResponseDto.success();
    }
}
