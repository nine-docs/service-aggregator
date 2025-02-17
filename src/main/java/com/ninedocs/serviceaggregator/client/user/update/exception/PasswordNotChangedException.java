package com.ninedocs.serviceaggregator.client.user.update.exception;

import com.ninedocs.serviceaggregator.controller.common.exception.CustomException;

public class PasswordNotChangedException extends CustomException {

  @Override
  public String getErrorCode() {
    return "PASSWORD_NOT_CHANGED";
  }
}
