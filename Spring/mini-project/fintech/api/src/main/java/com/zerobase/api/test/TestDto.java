package com.zerobase.api.test;

import com.zerobase.domain.domain.UserInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TestDto {

    @Getter
    @NoArgsConstructor
    public static class UserInfoDto {

        private String userKey;
        private String userRegistrationNumber;
        private String userName;
        private Long userIncomeAmount;

        @Builder
        private UserInfoDto(String userKey, String userRegistrationNumber, String userName, Long userIncomeAmount) {
            this.userKey = userKey;
            this.userRegistrationNumber = userRegistrationNumber;
            this.userName = userName;
            this.userIncomeAmount = userIncomeAmount;
        }

        public static UserInfoDto of(UserInfo userInfo) {
            return UserInfoDto.builder()
                .userKey(userInfo.getUserKey())
                .userRegistrationNumber(userInfo.getUserRegistrationNumber())
                .userName(userInfo.getUserName())
                .userIncomeAmount(userInfo.getUserIncomeAmount())
                .build();
        }
    }
}
