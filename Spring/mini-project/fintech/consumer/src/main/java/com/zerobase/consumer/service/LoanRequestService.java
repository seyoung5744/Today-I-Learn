package com.zerobase.consumer.service;

import com.zerobase.domain.domain.LoanReview;
import com.zerobase.domain.repository.LoanReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanRequestService {

    private final LoanReviewRepository loanReviewRepository;

    public void loanRequest() {
        // TODO : CB Component 로 요청 보내기 -> 응답값은 DB에 저장하기
    }

    public void loanRequestToCb() {
        // TODO
    }

    private void saveLoanReviewDate(LoanReview loanReview) {
        loanReviewRepository.save(loanReview);
    }
}
