package com.ninedocs.serviceaggregator.controller.register.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class VerificationCodeCreateRequest {

  @Schema(example = "test@email.com")
  @NotNull
  @Email
  @Size(max = 255, min = 1)
  private String email;
}
