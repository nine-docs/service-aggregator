package com.ninedocs.serviceaggregator.client.article.articlesummariesquery.dto;

import lombok.Getter;

@Getter
public class ArticleSummaryResponse {

  private String title;
  private CategoryResponse category;


  @Getter
  public static class CategoryResponse {

    private Long id;
    private String name;
  }
}
