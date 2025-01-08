package com.ninedocs.serviceaggregator.controller.register.emailverification.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailVerifiedResponse {

  private LocalDateTime verificationExpiredAt;
}
