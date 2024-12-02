package com.zerobase.api.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomErrorCode {

    RESULT_NOT_FOUND(HttpStatus.BAD_REQUEST, "E001", "result not found");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;
}
