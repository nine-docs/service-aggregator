package com.ninedocs.serviceaggregator.controller.register.register.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterResponse {

  private Long userId;
  private String accessToken;
  private LocalDateTime accessTokenExpiredAt;
}
