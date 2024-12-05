package com.zerobase.api.loan.request;

import com.zerobase.domain.domain.UserInfo;
import com.zerobase.kafka.dto.LoanRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoDto {

    private String userKey;
    private String userName;
    private Long userIncomeAmount;
    private String userRegistrationNumber;

    public UserInfoDto(String userKey, String userName, Long userIncomeAmount, String userRegistrationNumber) {
        this.userKey = userKey;
        this.userName = userName;
        this.userIncomeAmount = userIncomeAmount;
        this.userRegistrationNumber = userRegistrationNumber;
    }

    public UserInfo toEntity() {
        return UserInfo.builder()
            .userKey(userKey)
            .userName(userName)
            .userIncomeAmount(userIncomeAmount)
            .userRegistrationNumber(userRegistrationNumber)
            .build();
    }

    public LoanRequestDto toLoanRequestKafkaDto() {
        return LoanRequestDto.builder()
            .userKey(userKey)
            .userName(userName)
            .userIncomeAmount(userIncomeAmount)
            .userRegistrationNumber(userRegistrationNumber)
            .build();
    }
}
