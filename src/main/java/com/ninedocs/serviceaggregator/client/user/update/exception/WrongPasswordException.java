package com.ninedocs.serviceaggregator.client.user.update.exception;

import com.ninedocs.serviceaggregator.controller.common.exception.CustomException;

public class WrongPasswordException extends CustomException {

  @Override
  public String getErrorCode() {
    return "WRONG_PASSWORD";
  }
}
