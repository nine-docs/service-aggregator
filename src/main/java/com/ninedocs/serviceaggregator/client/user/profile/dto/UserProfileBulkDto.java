package com.ninedocs.serviceaggregator.client.user.profile.dto;

import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserProfileBulkDto {

  private final Map<Long, UserProfileResponse> map;

  public String getNicknameByUserId(Long userId, String anonymousNickname) {
    if (map.containsKey(userId)) {
      return map.get(userId).getNickname();
    }
    return anonymousNickname;
  }
}
