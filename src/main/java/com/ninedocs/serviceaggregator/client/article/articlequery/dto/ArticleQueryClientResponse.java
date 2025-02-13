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
  // Todo Category
}
