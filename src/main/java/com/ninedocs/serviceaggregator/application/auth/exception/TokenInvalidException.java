package com.ninedocs.serviceaggregator.application.auth.exception;

import com.ninedocs.serviceaggregator.controller.common.exception.CustomException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenInvalidException extends CustomException {

  private final InvalidCause invalidCause;

  @Override
  public String getErrorCode() {
    if (invalidCause == null || invalidCause == InvalidCause.UNKNOWN) {
      return InvalidCause.UNKNOWN.getDescription();
    }
    return invalidCause.getDescription();
  }

  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  @Getter
  public enum InvalidCause {
    TOKEN_EXPIRED("AUTHORIZATION_FAILED_BY_TOKEN_EXPIRED"),
    UNKNOWN("AUTHORIZATION_FAILED_BY_UNKNOWN_REASON");

    private final String description;
  }
}
