package com.ninedocs.serviceaggregator.client.user.register.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpRequest {

  private String email;
  private String nickname;
  private String password;
}
