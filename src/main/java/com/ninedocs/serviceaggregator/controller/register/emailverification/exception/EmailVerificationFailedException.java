package com.ninedocs.serviceaggregator.controller.register.emailverification.exception;

import com.ninedocs.serviceaggregator.controller.common.exception.CustomException;

public class EmailVerificationFailedException extends CustomException {

  @Override
  public String getErrorCode() {
    return "VERIFICATION_FAILED";
  }
}
