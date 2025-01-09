package com.ninedocs.serviceaggregator.client.user.signin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInRequest {

  private String email;
  private String password;
}
