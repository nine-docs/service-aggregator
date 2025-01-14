package com.ninedocs.serviceaggregator.client.common.error;

import com.ninedocs.serviceaggregator.controller.common.exception.CustomException;

public class Unknown2xxErrorException extends CustomException {

  @Override
  public String getErrorCode() {
    return "UNKNOWN_2XX_ERROR";
  }
}
