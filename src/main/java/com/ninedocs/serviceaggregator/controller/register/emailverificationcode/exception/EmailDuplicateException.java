package com.ninedocs.serviceaggregator.controller.register.emailverificationcode.exception;

import com.ninedocs.serviceaggregator.controller.common.exception.CustomException;

public final class EmailDuplicateException extends CustomException {

  @Override
  public String getErrorCode() {
    return "EMAIL_DUPLICATED";
  }
}
