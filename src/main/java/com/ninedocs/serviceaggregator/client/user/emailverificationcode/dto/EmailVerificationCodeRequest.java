package com.ninedocs.serviceaggregator.client.user.emailverificationcode.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailVerificationCodeRequest {

  private String email;
}
