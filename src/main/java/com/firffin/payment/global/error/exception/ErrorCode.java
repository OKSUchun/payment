package com.firffin.payment.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

/**
 * ErrorCode 오류 상황을 정의.
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
  SAMPLE_ERROR(BAD_REQUEST, "Sample Error Message");

    /**
     * 오류 HTTP 상태 코드.
     */
    private final HttpStatus status;
    /**
     * 오류 메시지.
     */
    private final String message;
}
