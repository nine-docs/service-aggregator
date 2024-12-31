package com.ninedocs.serviceaggregator.controller.login.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LoginFailedExceptionTest {

  @Test
  void getErrorCode() {
    assertThat(new LoginFailedException().getErrorCode()).isEqualTo("LOGIN_FAILED");
  }
}