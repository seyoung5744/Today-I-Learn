package com.zerobase.api.loan.request;

import com.zerobase.api.loan.GenerateKey;
import com.zerobase.api.loan.encrypt.EncryptComponent;
import com.zerobase.api.loan.request.LoanRequestDto.LoanRequestInputDto;
import com.zerobase.api.loan.request.LoanRequestDto.LoanRequestResponseDto;
import com.zerobase.domain.domain.UserInfo;
import com.zerobase.domain.repository.UserInfoRepository;
import com.zerobase.kafka.enums.KafkaTopic;
import com.zerobase.kafka.producer.LoanRequestSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanRequestServiceImpl implements LoanRequestService {

    private final GenerateKey generateKey;
    private final UserInfoRepository userInfoRepository;
    private final EncryptComponent encryptComponent;
    private final LoanRequestSender loanRequestSender;

    @Override
    public LoanRequestResponseDto loanRequestMain(LoanRequestInputDto loanRequestInputDto) {
        String userKey = generateKey.generateUserKey();

        loanRequestInputDto.setUserRegistrationNumber(
            encryptComponent.encryptString(loanRequestInputDto.getUserRegistrationNumber())
        );

        UserInfoDto userInfoDto = loanRequestInputDto.toUserInfoDto(userKey);

        saveUserInfo(userInfoDto);

        loanRequestReview(userInfoDto);

        return new LoanRequestDto.LoanRequestResponseDto(userKey);
    }

    @Override
    public UserInfo saveUserInfo(UserInfoDto userInfoDto) {
        return userInfoRepository.save(
            userInfoDto.toEntity()
        );
    }

    @Override
    public void loanRequestReview(UserInfoDto userInfoDto) {
        loanRequestSender.sendMessage(
            KafkaTopic.LOAN_REQUEST,
            userInfoDto.toLoanRequestKafkaDto()
        );
    }
}
