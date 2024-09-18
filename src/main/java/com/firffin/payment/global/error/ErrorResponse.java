package com.firffin.payment.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {

  private final String errorCodeName;
  private final String message;

  /**
   * ErrorCode의 이름과 메시지로 {@link ErrorResponse} 객체 생성.
   *
   * @param errorCodeName ErrorCode의 이름
   * @param message       ErrorCode의 메시지
   * @return 생성된 {@link ErrorResponse} 객체
   */
  public static ErrorResponse of(String errorCodeName, String message) {
    return new ErrorResponse(errorCodeName, message);
  }
}

