package com.ninedocs.serviceaggregator.controller.register.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EmailVerificationFailedExceptionTest {

  @Test
  void getErrorCode() {
    assertThat(new EmailVerificationFailedException().getErrorCode()).isEqualTo("VERIFICATION_FAILED");
  }
}
