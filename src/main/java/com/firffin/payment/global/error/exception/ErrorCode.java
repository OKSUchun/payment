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
    SAMPLE_ERROR(BAD_REQUEST, "Sample Error Message"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 에러가 발생하였습니다."),

    DUPLICATE_PHONE_NUMBER(CONFLICT, "이미 존재하는 핸드폰번호입니다."),

    UNREGISTERED_CHILD(BAD_REQUEST, "자녀 유저는 부모 유저가 먼저 등록해야합니다."),
    INVALID_ROLE(BAD_REQUEST, "유효한 권한이 아닙니다.");


    /**
     * 오류 HTTP 상태 코드.
     */
    private final HttpStatus status;
    /**
     * 오류 메시지.
     */
    private final String message;
}
