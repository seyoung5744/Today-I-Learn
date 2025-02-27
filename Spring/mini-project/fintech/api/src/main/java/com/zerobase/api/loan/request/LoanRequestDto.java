package com.zerobase.api.loan.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
public class LoanRequestDto {

    @Getter
    @NoArgsConstructor
//    @ToString
    public static class LoanRequestInputDto {

        private String userName;
        private Long userIncomeAmount;
        private String userRegistrationNumber;

        @Builder
        private LoanRequestInputDto(String userName, Long userIncomeAmount, String userRegistrationNumber) {
            this.userName = userName;
            this.userIncomeAmount = userIncomeAmount;
            this.userRegistrationNumber = userRegistrationNumber;
        }

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
