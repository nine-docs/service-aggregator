package com.ninedocs.serviceaggregator.controller.mypage.profile.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyProfileResponse {

  private String nickname;
  private String email;
}
