package com.ninedocs.serviceaggregator.controller.register.emailverificationcode.exception;

import static org.assertj.core.api.Assertions.assertThat;

import com.ninedocs.serviceaggregator.controller.register.emailverification.exception.EmailVerificationFailedException;
import org.junit.jupiter.api.Test;

class EmailVerificationFailedExceptionTest {

  @Test
  void getErrorCode() {
    assertThat(new EmailVerificationFailedException().getErrorCode()).isEqualTo("VERIFICATION_FAILED");
  }
}
