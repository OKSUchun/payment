package com.firffin.payment.global.error;

import com.firffin.payment.global.common.response.GlobalResponse;
import com.firffin.payment.global.error.exception.CustomException;
import com.firffin.payment.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 전역적 예외 처리 클래스
 * <p>{@link CustomException} 처리 후 예외별
 * 예외에 따라 맞는 HTTP 응답을 {@link GlobalResponse}로 감싸 반환.
 * </p>
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * {@link CustomException} 예외 처리 1. {@link CustomException}의 {@link ErrorCode} 를 통해
     * ErrorResponse
     * 생성 2. {@link GlobalResponse}로 ResponseBody 변환 3. Http 응답 코드와 실패 응답 반환</p>
     *
     * @param e {@link CustomException} 예외 객체
     * @return HTTP 상태 코드, {@link GlobalResponse} 포함한 ResponseEntity
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<GlobalResponse> handleCustomException(CustomException e) {
        final ErrorCode errorCode = e.getErrorCode();

        // ErrorCode를 기반으로 ErrorResponse 생성
        final ErrorResponse errorResponse =
            ErrorResponse.of(
                errorCode.name(), errorCode.getMessage());

        // 실패 응답 생성
        final GlobalResponse response =
            GlobalResponse.fail(errorCode.getStatus().value(), errorResponse);

        // ResponseEntity 에 Http 응답 코드와 실패 응답 반환
        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }

}

