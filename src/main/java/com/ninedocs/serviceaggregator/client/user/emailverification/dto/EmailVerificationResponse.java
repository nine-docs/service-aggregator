package com.ninedocs.serviceaggregator.client.user.emailverification.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class EmailVerificationResponse {

  private LocalDateTime verificationExpiredAt;
}
