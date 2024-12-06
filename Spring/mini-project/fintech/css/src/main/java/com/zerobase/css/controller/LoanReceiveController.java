package com.zerobase.css.controller;

import com.zerobase.css.dto.LoanRequestDto;
import com.zerobase.css.dto.LoanResultDto;
import com.zerobase.css.service.LoanReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/css/api/v1")
public class LoanReceiveController {

    private final LoanReviewService loanReviewService;

    @PostMapping("/request")
    public LoanResultDto.ResponseDto loanReceive(@RequestBody LoanRequestDto.RequestInputDto requestInputDto) {
        return loanReviewService.loanReview(requestInputDto);
    }
}
