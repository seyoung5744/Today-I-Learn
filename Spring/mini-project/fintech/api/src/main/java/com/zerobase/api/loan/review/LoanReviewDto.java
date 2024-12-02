package com.zerobase.api.loan.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class LoanReviewDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoanReviewResponseDto {

        private String userKey;
        private LoanResult loanResult;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoanResult {

        private Long userLimitAmount;
        private Double userLoanInterest;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoanReview {

        private String userKey;
        private Long loanLimitAmount;
        private Double userLoanInterest;

    }
}
