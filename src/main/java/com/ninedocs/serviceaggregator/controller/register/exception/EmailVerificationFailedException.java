package com.ninedocs.serviceaggregator.controller.register.exception;

import com.ninedocs.serviceaggregator.controller.common.exception.CustomException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EmailVerificationFailedException extends CustomException {

  private final String errorCode;
}
