package com.ninedocs.serviceaggregator.client.user.signin.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class SignInResponse {

  private String token;
  private LocalDateTime accessTokenExpiredAt;
}
