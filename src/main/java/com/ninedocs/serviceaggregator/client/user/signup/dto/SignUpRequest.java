package com.ninedocs.serviceaggregator.client.user.signup.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpRequest {

  private String email;
  private String nickname;
  private String password;
}
