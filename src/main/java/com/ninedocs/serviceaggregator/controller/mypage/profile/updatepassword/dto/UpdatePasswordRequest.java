package com.ninedocs.serviceaggregator.controller.mypage.profile.updatepassword.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdatePasswordRequest {

  @Schema(example = "test-password-1")
  @NotNull
  @Size(min = 1, max = 100)
  private String originalPassword;

  @Schema(example = "test-password-2")
  @NotNull
  @Size(min = 8, max = 50)
  private String newPassword;
}
