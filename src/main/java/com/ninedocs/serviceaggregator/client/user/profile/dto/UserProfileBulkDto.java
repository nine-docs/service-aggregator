package com.ninedocs.serviceaggregator.client.user.profile.dto;

import java.util.Map;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserProfileBulkDto {

  private final Map<Long, UserProfileResponse> map;

  public String getNicknameByUserId(Long userId, String anonymousNickname) {
    if (Objects.nonNull(userId) && map.containsKey(userId)) {
      return map.get(userId).getNickname();
    }
    return anonymousNickname;
  }
}
