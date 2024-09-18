package com.firffin.payment.global.common.response;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * <p>{@link RestControllerAdvice} 구현.
 * 패키지("firfin") 내의 모든 ControllerAdvice 적용.
 * <p>성공한 API 응답 표준화된 형식 반환.<br>
 */
@RestControllerAdvice(basePackages = "firfin")
public class GlobalResponseAdvice implements ResponseBodyAdvice {

  /**
   * 모든 Controller mehtod의 응답 처리 적용 여부 결정.
   *
   * @param returnType    메서드의 returnType
   * @param converterType converterType
   * @return true -> 모든 Controller mehtod의 응답 처리 적용.
   */
  @Override
  public boolean supports(MethodParameter returnType, Class converterType) {
    return true;
  }

  /**
   * 응답 바디가 작성되기 전 호출-> 응답 수정 메서드.
   *
   * <p>1. HTTP 상태 코드 확인
   * 2-1. 2xx 이외의 상태 코드 or ResponseBody String -> 원본 응답 반환.</p> 2-2. 2xx 성공 응답-> ResponseBody
   * {@link GlobalResponse#success(int, Object)} wrapping
   *
   * @param body                  응답 바디
   * @param returnType            메서드의 반환 타입
   * @param selectedContentType   선택된 콘텐츠 타입
   * @param selectedConverterType 선택된 변환기 타입
   * @param request               현재의 HTTP 요청
   * @param response              현재의 HTTP 응답
   * @return 수정된 응답 바디 또는 원래의 응답 바디
   */
  @Override
  public Object beforeBodyWrite(
      Object body,
      MethodParameter returnType,
      MediaType selectedContentType,
      Class selectedConverterType,
      ServerHttpRequest request,
      ServerHttpResponse response) {

    HttpServletResponse servletResponse =
        ((ServletServerHttpResponse) response).getServletResponse();
    // 1
    int status = servletResponse.getStatus();
    HttpStatus resolve = HttpStatus.resolve(status);

    // 2-1
    if (resolve == null || body instanceof String) {
      return body;
    }
    // 2-2
    if (resolve.is2xxSuccessful()) {
      return GlobalResponse.success(status, body);
    }
    return body;
  }
}
