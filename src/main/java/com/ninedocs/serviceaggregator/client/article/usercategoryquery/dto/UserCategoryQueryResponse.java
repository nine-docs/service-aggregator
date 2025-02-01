package com.ninedocs.serviceaggregator.client.article.usercategoryquery.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class UserCategoryQueryResponse {

  private Long userId;
  private List<CategoryResponse> categories;

  @Getter
  public static class CategoryResponse {

    private Long id;
    private String title;
  }
}
