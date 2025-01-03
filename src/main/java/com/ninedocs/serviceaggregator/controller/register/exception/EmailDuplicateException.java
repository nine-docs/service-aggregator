package com.ninedocs.serviceaggregator.controller.register.exception;

import com.ninedocs.serviceaggregator.controller.common.exception.CustomException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public final class EmailDuplicateException extends CustomException {

  @Override
  public String getErrorCode() {
    return "EMAIL_DUPLICATED";
  }
}
