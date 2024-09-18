package com.firffin.payment.global.common.response;

import com.firffin.payment.global.error.ErrorResponse;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GlobalResponse {

  /**
   * 모든 API 응답에 공통된 구조로 생성.
   */
  private boolean success;
  private int status;
  private Object data;
  private LocalDateTime timestamp;

  /**
   * 성공 응답 생성
   *
   * @param status HTTP 상태 코드
   * @param data   응답 데이터
   * @return 성공 {@link GlobalResponse} 객체
   */
  public static GlobalResponse success(int status, Object data) {
    return GlobalResponse.builder()
        .success(true)
        .status(status)
        .data(data)
        .timestamp(LocalDateTime.now())
        .build();
  }

  /**
   * 실패 응답 생성
   *
   * @param status        HTTP 상태 코드
   * @param errorResponse 오류 정보 객체 {@link ErrorResponse}
   * @return 실패 {@link GlobalResponse} 객체
   */
  public static GlobalResponse fail(int status, ErrorResponse errorResponse) {
    return GlobalResponse.builder()
        .success(false)
        .status(status)
        .data(errorResponse)
        .timestamp(LocalDateTime.now())
        .build();
  }
}
