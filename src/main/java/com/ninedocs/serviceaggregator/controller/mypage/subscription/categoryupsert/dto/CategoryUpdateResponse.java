package com.ninedocs.serviceaggregator.controller.mypage.subscription.categoryupsert.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryUpdateResponse {

  private List<CategoryResponse> categories;

  @Getter
  @Builder
  public static class CategoryResponse {

    private Long id;
    private String name;
  }
}
