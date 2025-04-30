package com.housing.back.service.implement;

import com.housing.back.common.CertificationNumber;
import com.housing.back.dto.request.auth.CheckCertificationRequestDto;
import com.housing.back.dto.request.auth.EmailCertificationRequestDto;
import com.housing.back.dto.request.auth.IdCheckRequestDto;
import com.housing.back.dto.response.ResponseDto;
import com.housing.back.dto.response.auth.CheckCertificationResponseDto;
import com.housing.back.dto.response.auth.EmailCertificationResponseDto;
import com.housing.back.dto.response.auth.IdCheckResponseDto;
import com.housing.back.entity.CertificationEntity;
import com.housing.back.provider.EmailProvider;
import com.housing.back.repository.CertificationRepository;
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
    private final CertificationRepository certificationRepository;

    private final EmailProvider emailProvider;

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

    @Override
    public ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto request) {
        try {

            String userId = request.getId();
            String email = request.getEmail();

            boolean isExisted = userRepository.existsByUserId(userId);
            if (isExisted) {
                return EmailCertificationResponseDto.duplicateId();
            }

            String certificationNumber = CertificationNumber.getCertificationNumber();

            boolean isSuccessed = emailProvider.sendCertificationMail(email, certificationNumber);
            if (!isSuccessed) {
                return EmailCertificationResponseDto.mailSendFail();
            }

            CertificationEntity certificationEntity = new CertificationEntity(userId, email, certificationNumber);
            certificationRepository.save(certificationEntity);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception [Err_Msg]: {}", e.getMessage());
            return ResponseDto.databaseError();
        }

        return EmailCertificationResponseDto.success();
    }

    @Override
    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto request) {
        try {

            String userId = request.getId();
            String email = request.getEmail();
            String certificationNumber = request.getCertificationNumber();

            CertificationEntity certificationEntity = certificationRepository.findByUserId(userId);
            if (certificationEntity == null) {
                return CheckCertificationResponseDto.certificationFail();
            }

            boolean isMatched = certificationEntity.getEmail().equals(email) && certificationEntity.getCertificationNumber().equals(certificationNumber);
            if(!isMatched) {
                return CheckCertificationResponseDto.certificationFail();
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception [Err_Msg]: {}", e.getMessage());
            return ResponseDto.databaseError();
        }
        return CheckCertificationResponseDto.success();
    }
}
