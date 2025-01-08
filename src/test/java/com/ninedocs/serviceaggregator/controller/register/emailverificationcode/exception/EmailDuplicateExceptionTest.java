package com.ninedocs.serviceaggregator.controller.register.emailverificationcode.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class EmailDuplicateExceptionTest {

  @Test
  void getErrorCode() {
    assertThat(new EmailDuplicateException().getErrorCode()).isEqualTo("EMAIL_DUPLICATED");
  }
}
