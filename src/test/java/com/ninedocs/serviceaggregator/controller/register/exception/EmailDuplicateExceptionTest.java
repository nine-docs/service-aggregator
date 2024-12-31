package com.ninedocs.serviceaggregator.controller.register.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EmailDuplicateExceptionTest {

  @Test
  void getErrorCode() {
    assertThat(new EmailDuplicateException().getErrorCode()).isEqualTo("EMAIL_DUPLICATED");
  }
}
