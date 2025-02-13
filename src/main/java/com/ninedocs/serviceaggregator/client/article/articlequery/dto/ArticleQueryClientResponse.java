package com.ninedocs.serviceaggregator.client.article.articlequery.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleQueryClientResponse {

  private Long id;
  private String title;
  private String content;
  private String link;
  private CategoryClientResponse category;

  @Getter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class CategoryClientResponse {

    private Long id;
    private String name;
  }
}
