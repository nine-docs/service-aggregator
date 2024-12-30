package com.ninedocs.serviceaggregator.controller.common.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonResponse<T> {

  private boolean success;
  private String errorCode;
  private T data;

  public static <T> CommonResponse<T> success(T data) {
    return new CommonResponse<>(true, null, data);
  }

  public static CommonResponse<Void> success() {
    return new CommonResponse<>(true, null, null);
  }

  public static CommonResponse<Void> error(String errorCode) {
    return new CommonResponse<>(false, errorCode, null);
  }
}
