package com.ninedocs.serviceaggregator.client.user.emailverification.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailVerificationRequest {

  private String email;
  private String emailVerificationCode;
}
