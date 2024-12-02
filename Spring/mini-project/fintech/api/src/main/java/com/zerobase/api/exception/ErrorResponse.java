package com.zerobase.api.exception;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final CustomException customException;

    public ResponseEntity<ErrorResponseDto> toResponseEntity() {
        return ResponseEntity
            .status(customException.getCustomErrorCode().getHttpStatus())
            .body(
                new ErrorResponseDto(
                    customException.getCustomErrorCode().getErrorCode(),
                    customException.getCustomErrorCode().getErrorMessage()
                )
            );
    }

    @Getter
    public static class ErrorResponseDto {

        private final String errorCode;
        private final String errorMessage;
        private final LocalDateTime timeStamp;

        public ErrorResponseDto(String errorCode, String errorMessage) {
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
            this.timeStamp = LocalDateTime.now();
        }
    }
}
