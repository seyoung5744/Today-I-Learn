package com.zerobase.api.loan.review;

import com.zerobase.api.loan.review.LoanReviewDto.LoanResult;
import com.zerobase.api.loan.review.LoanReviewDto.LoanReviewResponseDto;
import com.zerobase.domain.domain.LoanReview;
import com.zerobase.domain.repository.LoanReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanReviewServiceImpl implements LoanReviewService {

    private final LoanReviewRepository loanReviewRepository;

    @Override
    public LoanReviewResponseDto loanReviewMain(String userKey) {
        LoanReviewDto.LoanReview loanResult = getLoanResult(userKey);

        return new LoanReviewResponseDto(
            userKey,
            new LoanResult(
                loanResult.getLoanLimitAmount(),
                loanResult.getUserLoanInterest()
            )
        );
    }

    @Override
    public LoanReviewDto.LoanReview getLoanResult(String userKey) {
        LoanReview loanReview = loanReviewRepository.findByUserKey(userKey);

        return new LoanReviewDto.LoanReview(
            loanReview.getUserKey(),
            loanReview.getLoanLimitAmount(),
            loanReview.getLoanInterest()
        );
    }
}
