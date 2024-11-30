package com.zerobase.api.loan.request;

import com.zerobase.domain.domain.UserInfo;

public interface LoanRequestService {

    LoanRequestDto.LoanRequestResponseDto loanRequestMain(LoanRequestDto.LoanRequestInputDto loanRequestInputDto);

    UserInfo saveUserInfo(UserInfoDto userInfoDto);

    void loanRequestReview(String userKey);
}
