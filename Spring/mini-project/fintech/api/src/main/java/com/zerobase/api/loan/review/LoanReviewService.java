package com.zerobase.api.loan.review;

import com.zerobase.domain.domain.LoanReview;
import java.util.Optional;

public interface LoanReviewService {

    LoanReviewDto.LoanReviewResponseDto loanReviewMain(String userKey);

    Optional<LoanReview> getLoanResult(String userKey);
}
