package com.ninedocs.serviceaggregator.controller.mypage.profile.updatenickname.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateNicknameRequest {

  @Schema(example = "testnickname")
  @NotNull
  @Size(min = 1, max = 50)
  private String newNickname;
}
