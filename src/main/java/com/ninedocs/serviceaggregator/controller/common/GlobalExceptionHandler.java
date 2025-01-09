package com.ninedocs.serviceaggregator.controller.common;

import com.ninedocs.serviceaggregator.client.common.error.ApiErrorException;
import com.ninedocs.serviceaggregator.controller.common.exception.CustomException;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomException.class)
  public Mono<ResponseEntity<ApiResponse<Void>>> handleCustomException(CustomException e) {
    return Mono.just(ResponseEntity.ok(ApiResponse.error(e.getErrorCode())));
  }

  @ExceptionHandler(ApiErrorException.class)
  public ResponseEntity<String> handleResponseStatusException(ApiErrorException e) {
    log.error(
        "# {} 도메인에 {} 요청 시 {} Error 발생 : {}",
        e.getDomainName(), e.getRequestUri().getPath(), e.getStatusCode(), e.getReason()
    );
    return ResponseEntity
        .status(e.getStatusCode())
        .body(e.getReason());
  }
}
