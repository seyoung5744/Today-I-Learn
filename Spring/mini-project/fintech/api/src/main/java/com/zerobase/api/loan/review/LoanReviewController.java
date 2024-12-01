package com.zerobase.api.loan.review;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/fintech/api/v1")
public class LoanReviewController {

    private final LoanReviewService loanReviewService;

    @GetMapping("/review/{userKey}")
    public ResponseEntity<LoanReviewDto.LoanReviewResponseDto> getReviewData(
        @PathVariable String userKey
    ) {
        return ResponseEntity.ok(
            loanReviewService.loanReviewMain(userKey)
        );
    }
}
