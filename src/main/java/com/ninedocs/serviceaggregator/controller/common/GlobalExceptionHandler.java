package com.ninedocs.serviceaggregator.controller.common;

import com.ninedocs.serviceaggregator.client.common.error.ApiErrorException;
import com.ninedocs.serviceaggregator.client.common.error.Unknown2xxErrorException;
import com.ninedocs.serviceaggregator.controller.common.exception.CustomException;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomException.class)
  public Mono<ResponseEntity<ApiResponse<Void>>> handleCustomException(CustomException e) {
    return Mono.just(ResponseEntity.ok(ApiResponse.error(e.getErrorCode())));
  }

  @ExceptionHandler(ApiErrorException.class)
  public Mono<ResponseEntity<String>> handleResponseStatusException(ApiErrorException e) {
    log.error(
        "# {} 도메인에 {} 요청 시 {} Error 발생 : {}",
        e.getDomainName(), e.getRequestUri().getPath(), e.getStatusCode(), e.getReason()
    );
    return Mono.just(
        ResponseEntity.status(e.getStatusCode())
            .body(e.getReason())
    );
  }

  @ExceptionHandler(Unknown2xxErrorException.class)
  public Mono<ResponseEntity<String>> handleUnknown2xxErrorException(
      Unknown2xxErrorException e
  ) {
    log.error("# {} 도메인에 {} 요청 시 Unknown2xxErrorException Error 발생 - errorCode: {}",
        e.getDomainName(), e.getRequestUri(), e.getErrorCode()
    );
    return Mono.just(
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .body(e.getErrorCode())
    );
  }

  @ExceptionHandler(WebExchangeBindException.class)
  public Mono<ResponseEntity<String>> handleSpringValidatorError(
      WebExchangeBindException e
  ) {
    List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

    String errorMessage = !fieldErrors.isEmpty()
        ? fieldErrors.get(0).getDefaultMessage()
        : e.getMessage();

    return Mono.just(
        ResponseEntity.status(e.getStatusCode())
            .body(errorMessage)
    );
  }
}
