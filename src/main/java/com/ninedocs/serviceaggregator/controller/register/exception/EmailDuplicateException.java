package com.ninedocs.serviceaggregator.controller.register.exception;

import com.ninedocs.serviceaggregator.controller.common.exception.CustomException;
import lombok.Getter;

@Getter
public final class EmailDuplicateException extends CustomException {

  private final String errorCode;

  public EmailDuplicateException(String errorCode) {
    this.errorCode = errorCode;
  }
}
