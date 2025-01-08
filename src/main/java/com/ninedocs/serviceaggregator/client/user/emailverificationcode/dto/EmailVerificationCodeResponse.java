package com.ninedocs.serviceaggregator.client.user.emailverificationcode.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class EmailVerificationCodeResponse {

  private LocalDateTime verificationCodeExpiredAt;
}
