package com.ninedocs.serviceaggregator.controller.common.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpApiResponse<T> {

  private boolean success;
  private String errorCode;
  private T data;

  public static <T> HttpApiResponse<T> success(T data) {
    return new HttpApiResponse<>(true, null, data);
  }

  public static HttpApiResponse<Void> success() {
    return new HttpApiResponse<>(true, null, null);
  }

  public static HttpApiResponse<Void> error(String errorCode) {
    return new HttpApiResponse<>(false, errorCode, null);
  }
}
