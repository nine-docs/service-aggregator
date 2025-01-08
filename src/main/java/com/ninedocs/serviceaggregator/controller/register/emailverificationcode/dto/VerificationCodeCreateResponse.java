package com.ninedocs.serviceaggregator.controller.register.emailverificationcode.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VerificationCodeCreateResponse {

  private LocalDateTime verificationCodeExpiredAt;
}
