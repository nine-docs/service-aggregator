package com.ninedocs.serviceaggregator.application.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtDecodeResult {

  private Boolean isValid;
  private JwtInfo jwtInfo;

  @Getter
  @Builder
  public static class JwtInfo {

    private Long userId;
  }
}
