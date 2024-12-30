package com.ninedocs.serviceaggregator.controller.common.response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommonResponseTest {

  @DisplayName("body가 없는 성공 응답")
  @Test
  void success() {
    CommonResponse<Void> result = CommonResponse.success();

    assertThat(result.isSuccess()).isTrue();
    assertThat(result.getErrorCode()).isNull();
    assertThat(result.getData()).isNull();
  }

  @DisplayName("body가 있는 성공 응답")
  @Test
  void successWithBody() {
    CommonResponse<String> result = CommonResponse.success("data");

    assertThat(result.isSuccess()).isTrue();
    assertThat(result.getErrorCode()).isNull();
    assertThat(result.getData()).isEqualTo("data");
  }

  @DisplayName("커스텀 에러 응답")
  @Test
  void error() {
    CommonResponse<Void> result = CommonResponse.error("TEST_ERROR_CODE");

    assertThat(result.isSuccess()).isFalse();
    assertThat(result.getErrorCode()).isEqualTo("TEST_ERROR_CODE");
    assertThat(result.getData()).isNull();
  }
}