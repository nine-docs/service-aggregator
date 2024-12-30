package com.ninedocs.serviceaggregator.controller.register.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VerificationCodeCreateResponse {

  private LocalDateTime verificationCodeExpiredAt;
}
