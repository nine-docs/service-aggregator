package com.ninedocs.serviceaggregator.controller.register.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RegisterRequest {

  @Schema(example = "test@email.com")
  @NotNull
  @Size(min = 1, max = 255)
  @Email
  private String email;

  @Schema(example = "sweetpotato98")
  @NotNull
  @Size(min = 1, max = 50)
  private String nickname;

  @Schema(example = "test-password")
  @NotNull
  @Size(min = 8, max = 50)
  private String password;
}
