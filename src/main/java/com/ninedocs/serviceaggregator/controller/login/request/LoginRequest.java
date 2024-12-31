package com.ninedocs.serviceaggregator.controller.login.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginRequest {

  @Schema(example = "test@email.com")
  @NotNull
  @Size(min = 1, max = 255)
  @Email
  private String email;

  @Schema(example = "test-password")
  @NotNull
  @Size(min = 1, max = 255)
  private String password;
}
