package com.zerobase.consumer.dto;

import com.zerobase.domain.domain.LoanReview;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewResponseDto {

    private String userKey;
    private Double interest;
    private Long limitAmount;

    public LoanReview toLoanReviewEntity() {
        return LoanReview.builder()
            .userKey(userKey)
            .loanInterest(interest)
            .loanLimitAmount(limitAmount)
            .build();
    }
}
