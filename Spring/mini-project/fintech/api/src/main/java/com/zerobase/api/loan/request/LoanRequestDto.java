package com.zerobase.api.loan.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class LoanRequestDto {

    @Getter
    @NoArgsConstructor
    public static class LoanRequestInputDto {

        private String userName;
        private Long userIncomeAmount;
        private String userRegistrationNumber;

        public UserInfoDto toUserInfoDto(String userKey) {
            return new UserInfoDto(userKey, userName, userIncomeAmount, userRegistrationNumber);
        }

        public void setUserRegistrationNumber(String userRegistrationNumber) {
            this.userRegistrationNumber = userRegistrationNumber;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class LoanRequestResponseDto {

        private String userKey;

        public LoanRequestResponseDto(String userKey) {
            this.userKey = userKey;
        }
    }
}
