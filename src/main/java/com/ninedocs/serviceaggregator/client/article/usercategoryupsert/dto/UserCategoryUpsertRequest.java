package com.ninedocs.serviceaggregator.client.article.usercategoryupsert.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCategoryUpsertRequest {

  private Long userId;
  private String userEmail;
  private List<Long> categoryIds;
}
