package com.ninedocs.serviceaggregator.client.common.error;

import com.ninedocs.serviceaggregator.controller.common.exception.CustomException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Unknown2xxErrorException extends CustomException {

  private final String domainName;
  private final String requestUri;
  private final String domainErrorCode;

  @Override
  public String getErrorCode() {
    return "UNKNOWN_2XX_ERROR";
  }
}
