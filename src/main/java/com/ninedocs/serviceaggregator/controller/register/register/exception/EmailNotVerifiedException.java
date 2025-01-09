package com.ninedocs.serviceaggregator.controller.register.register.exception;

import com.ninedocs.serviceaggregator.controller.common.exception.CustomException;

public class EmailNotVerifiedException extends CustomException {

  @Override
  public String getErrorCode() {
    return "EMAIL_NOT_VERIFIED";
  }
}
