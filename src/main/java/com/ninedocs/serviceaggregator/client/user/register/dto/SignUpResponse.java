package com.ninedocs.serviceaggregator.client.user.register.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class SignUpResponse {

  private Long id;
  private String token;
  private LocalDateTime accessTokenExpiredAt;
}
