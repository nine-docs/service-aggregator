package com.ninedocs.serviceaggregator.controller.login.exception;

import com.ninedocs.serviceaggregator.controller.common.exception.CustomException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class LoginFailedException extends CustomException {

  @Override
  public String getErrorCode() {
    return "LOGIN_FAILED";
  }
}
