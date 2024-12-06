package com.zerobase.css.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class LoanRequestDto {

    @Getter
    @NoArgsConstructor
    public static class RequestInputDto {

        private String userKey;
        private String userName;
        private Long userIncomeAmount;
        private String userRegistrationNumber;

        @Builder
        private RequestInputDto(String userKey, String userName, Long userIncomeAmount, String userRegistrationNumber) {
            this.userKey = userKey;
            this.userName = userName;
            this.userIncomeAmount = userIncomeAmount;
            this.userRegistrationNumber = userRegistrationNumber;
        }
    }


}
