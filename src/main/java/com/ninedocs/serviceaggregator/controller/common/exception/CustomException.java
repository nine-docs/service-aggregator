package com.ninedocs.serviceaggregator.controller.common.exception;

public abstract class CustomException extends RuntimeException {

  public abstract String getErrorCode();
}
