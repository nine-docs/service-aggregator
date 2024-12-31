package com.ninedocs.serviceaggregator.controller.common.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ApiResponseTest {

  @DisplayName("body가 없는 성공 응답")
  @Test
  void success() {
    ApiResponse<Void> result = ApiResponse.success();

    assertThat(result.isSuccess()).isTrue();
    assertThat(result.getErrorCode()).isNull();
    assertThat(result.getData()).isNull();
  }

  @DisplayName("body가 있는 성공 응답")
  @Test
  void successWithBody() {
    ApiResponse<String> result = ApiResponse.success("data");

    assertThat(result.isSuccess()).isTrue();
    assertThat(result.getErrorCode()).isNull();
    assertThat(result.getData()).isEqualTo("data");
  }

  @DisplayName("커스텀 에러 응답")
  @Test
  void error() {
    ApiResponse<Void> result = ApiResponse.error("TEST_ERROR_CODE");

    assertThat(result.isSuccess()).isFalse();
    assertThat(result.getErrorCode()).isEqualTo("TEST_ERROR_CODE");
    assertThat(result.getData()).isNull();
  }
}
