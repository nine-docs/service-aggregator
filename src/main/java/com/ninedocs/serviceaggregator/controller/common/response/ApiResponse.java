package com.ninedocs.serviceaggregator.controller.common.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

  private boolean success;
  private String errorCode;
  private T data;

  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<>(true, null, data);
  }

  public static ApiResponse<Void> success() {
    return new ApiResponse<>(true, null, null);
  }

  public static ApiResponse<Void> error(String errorCode) {
    return new ApiResponse<>(false, errorCode, null);
  }
}
