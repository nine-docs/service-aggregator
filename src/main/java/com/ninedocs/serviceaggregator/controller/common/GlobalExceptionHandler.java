package com.ninedocs.serviceaggregator.controller.common;

import com.ninedocs.serviceaggregator.controller.common.exception.CustomException;
import com.ninedocs.serviceaggregator.controller.common.response.HttpApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomException.class)
  public Mono<ResponseEntity<HttpApiResponse<Void>>> handleCustomException(CustomException e) {
    return Mono.just(ResponseEntity.ok(HttpApiResponse.error(e.getErrorCode())));
  }
}
