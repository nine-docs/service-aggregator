package com.ninedocs.serviceaggregator.controller.login.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

  private String accessToken;
  private LocalDateTime accessTokenExpiredAt;
}
