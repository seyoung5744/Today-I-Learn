package com.zerobase.api.loan.request;

import com.zerobase.api.loan.GenerateKey;
import com.zerobase.api.loan.encrypt.EncryptComponent;
import com.zerobase.api.loan.request.LoanRequestDto.LoanRequestInputDto;
import com.zerobase.api.loan.request.LoanRequestDto.LoanRequestResponseDto;
import com.zerobase.domain.domain.UserInfo;
import com.zerobase.domain.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanRequestServiceImpl implements LoanRequestService {

    private final GenerateKey generateKey;
    private final UserInfoRepository userInfoRepository;
    private final EncryptComponent encryptComponent;

    @Override
    public LoanRequestResponseDto loanRequestMain(LoanRequestInputDto loanRequestInputDto) {
        String userKey = generateKey.generateUserKey();

        loanRequestInputDto.setUserRegistrationNumber(
            encryptComponent.encryptString(loanRequestInputDto.getUserRegistrationNumber())
        );

        saveUserInfo(loanRequestInputDto.toUserInfoDto(userKey));

        loanRequestReview(userKey);

        return new LoanRequestDto.LoanRequestResponseDto(userKey);
    }

    @Override
    public UserInfo saveUserInfo(UserInfoDto userInfoDto) {
        return userInfoRepository.save(
            userInfoDto.toEntity()
        );
    }

    @Override
    public void loanRequestReview(String userKey) {

    }
}
