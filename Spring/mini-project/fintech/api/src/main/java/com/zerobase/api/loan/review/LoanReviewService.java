package com.zerobase.api.loan.review;

public interface LoanReviewService {

    LoanReviewDto.LoanReviewResponseDto loanReviewMain(String userKey);

    LoanReviewDto.LoanReview getLoanResult(String userKey);
}
