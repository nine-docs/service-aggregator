package com.ninedocs.serviceaggregator.client.user.update.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateNicknameClientRequest {

  private String newNickname;
}
