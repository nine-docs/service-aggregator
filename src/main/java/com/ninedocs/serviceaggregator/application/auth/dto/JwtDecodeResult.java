package com.ninedocs.serviceaggregator.application.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtDecodeResult {

  private Long userId;
}
