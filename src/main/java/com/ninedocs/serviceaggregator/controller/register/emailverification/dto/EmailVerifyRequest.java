package com.ninedocs.serviceaggregator.controller.register.emailverification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class EmailVerifyRequest {

  @Schema(example = "test@email.com")
  @NotNull
  @Size(min = 1, max = 255)
  @Email
  private String email;

  @Schema(example = "111111")
  @NotNull
  @Size(min = 6, max = 6)
  private String emailVerificationCode;
}
