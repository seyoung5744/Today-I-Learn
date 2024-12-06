package com.zerobase.css.service;

import com.zerobase.css.dto.LoanRequestDto.RequestInputDto;
import com.zerobase.css.dto.LoanResultDto.ResponseDto;
import org.springframework.stereotype.Service;

@Service
public class LoanReviewService {

    public ResponseDto loanReview(RequestInputDto requestInputDto) {
        if(requestInputDto.getUserIncomeAmount() < 0) throw new RuntimeException("Invaild userIncomeAmount Param");
        if (requestInputDto.getUserIncomeAmount() < 10000000) return new ResponseDto(requestInputDto.getUserKey(), 0.0, 10000000L);
        if (requestInputDto.getUserIncomeAmount() < 20000000) return new ResponseDto(requestInputDto.getUserKey(), 10.0, 20000000L);
        if (requestInputDto.getUserIncomeAmount() < 30000000) return new ResponseDto(requestInputDto.getUserKey(), 9.0, 30000000L);
        if (requestInputDto.getUserIncomeAmount() < 40000000) return new ResponseDto(requestInputDto.getUserKey(), 8.0, 40000000L);
        if (requestInputDto.getUserIncomeAmount() < 50000000) return new ResponseDto(requestInputDto.getUserKey(), 7.0, 50000000L);
        if (requestInputDto.getUserIncomeAmount() >= 50000000) return new ResponseDto(requestInputDto.getUserKey(), 6.0, 60000000L);
        throw new RuntimeException("Invalid userIncomeAmount Param");
    }
}
