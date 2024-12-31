package com.ninedocs.serviceaggregator.controller.register.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class EmailVerifyRequest {

  @Schema(example = "fail@email.com")
  @NotNull
  @Size(min = 1, max = 255)
  @Email
  private String email;

  @Schema(example = "123456")
  @NotNull
  @Size(min = 6, max = 6)
  private String emailVerificationCode;
}
