package com.zerobase.consumer.service;

import com.zerobase.consumer.dto.ReviewResponseDto;
import com.zerobase.domain.domain.LoanReview;
import com.zerobase.domain.repository.LoanReviewRepository;
import com.zerobase.kafka.dto.LoanRequestDto;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class LoanRequestService {

    private static final String CSS_URL = "http://localhost:8081/css/api/v1/request";

    private final LoanReviewRepository loanReviewRepository;

    public void loanRequest(LoanRequestDto loanRequestDto) {
        ReviewResponseDto reviewResult = loanRequestToCb(loanRequestDto);

        saveLoanReviewDate(reviewResult.toLoanReviewEntity());
    }

    private ReviewResponseDto loanRequestToCb(LoanRequestDto loanRequestDto) {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        builder.setConnectTimeout(Duration.ofMillis(1000));
        builder.setReadTimeout(Duration.ofMillis(1000));
        RestTemplate restTemplate = builder.build();

        return restTemplate.postForObject(CSS_URL, loanRequestDto, ReviewResponseDto.class);
    }

    private void saveLoanReviewDate(LoanReview loanReview) {
        loanReviewRepository.save(loanReview);
    }


}
