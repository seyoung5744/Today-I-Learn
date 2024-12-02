package com.zerobase.api.loan.review;

import com.zerobase.api.exception.CustomException;
import com.zerobase.api.exception.ErrorResponse;
import com.zerobase.api.exception.ErrorResponse.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {LoanReviewController.class})
public class LoanReviewControllerAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> customExceptionHandler(CustomException customException) {
        return new ErrorResponse(customException).toResponseEntity();
    }
}
