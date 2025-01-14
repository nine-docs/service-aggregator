package com.ninedocs.serviceaggregator.controller.common.exception;

public abstract class CustomException extends RuntimeException {

  public CustomException() {
    super();
  }

  public CustomException(String errorMessage) {
    super(errorMessage);
  }

  public abstract String getErrorCode();
}
