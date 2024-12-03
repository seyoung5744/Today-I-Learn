package com.zerobase.kafka.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoanRequestDto {

    private String userKey;
    private String userName;
    private Long userIncomeAmount;
    private String userRegistrationNumber;

    @Builder
    private LoanRequestDto(String userKey, String userName, Long userIncomeAmount, String userRegistrationNumber) {
        this.userKey = userKey;
        this.userName = userName;
        this.userIncomeAmount = userIncomeAmount;
        this.userRegistrationNumber = userRegistrationNumber;
    }
}
