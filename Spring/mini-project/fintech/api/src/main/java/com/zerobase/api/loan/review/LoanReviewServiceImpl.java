package com.zerobase.api.loan.review;

import com.zerobase.api.exception.CustomErrorCode;
import com.zerobase.api.exception.CustomException;
import com.zerobase.api.loan.review.LoanReviewDto.LoanResult;
import com.zerobase.api.loan.review.LoanReviewDto.LoanReviewResponseDto;
import com.zerobase.domain.domain.LoanReview;
import com.zerobase.domain.repository.LoanReviewRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanReviewServiceImpl implements LoanReviewService {

    private final LoanReviewRepository loanReviewRepository;

    @Override
    public LoanReviewResponseDto loanReviewMain(String userKey) {
        return new LoanReviewResponseDto(
            userKey,
            getLoanResult(userKey)
                .map(this::toResponseDto)
                .orElseThrow(() -> new CustomException(CustomErrorCode.RESULT_NOT_FOUND))
        );
    }

    @Override
    @Cacheable(value = {"REVIEW"}, key = "#userKey", cacheManager = "redisCacheManager")
    public Optional<LoanReview> getLoanResult(String userKey) {
        return loanReviewRepository.findByUserKey(userKey);
    }

    private LoanReviewDto.LoanResult toResponseDto(LoanReview loanReview) {
        return new LoanResult(
            loanReview.getLoanLimitAmount(),
            loanReview.getLoanInterest()
        );
    }
}
