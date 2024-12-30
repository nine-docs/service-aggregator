package com.ninedocs.serviceaggregator.controller.register.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class VerificationCodeCreateRequest {

  @NotNull
  @Email
  private String email;
}
