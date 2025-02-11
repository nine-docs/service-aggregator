package com.ninedocs.serviceaggregator.controller.article.article.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleQueryResponse {

  private String title;
  private String contents;
  private CategoryResponse category;

  @Getter
  @Builder
  public static class CategoryResponse {

    private Long id;
    private String title;
  }
}
