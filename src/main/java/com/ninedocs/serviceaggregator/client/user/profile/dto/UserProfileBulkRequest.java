package com.ninedocs.serviceaggregator.client.user.profile.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileBulkRequest {

  private List<Long> userIds;
}
