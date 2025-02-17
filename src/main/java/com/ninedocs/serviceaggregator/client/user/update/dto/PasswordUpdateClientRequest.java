package com.ninedocs.serviceaggregator.client.user.update.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PasswordUpdateClientRequest {

  private String originalPassword;
  private String newPassword;
}
