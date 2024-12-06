package com.zerobase.css.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class LoanResultDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseDto {

        private String userKey;
        private Double interest;
        private Long limitAmount;

    }
}
