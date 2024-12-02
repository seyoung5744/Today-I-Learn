package com.zerobase.api.loan.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/fintech/api/v1")
public class LoanRequestController {

    private final LoanRequestService loanRequestService;

    @PostMapping("/request")
    public ResponseEntity<LoanRequestDto.LoanRequestResponseDto> loanRequest(
        @RequestBody LoanRequestDto.LoanRequestInputDto loanRequestInputDto
    ) {
        return ResponseEntity.ok(
            loanRequestService.loanRequestMain(loanRequestInputDto)
        );
    }
}
