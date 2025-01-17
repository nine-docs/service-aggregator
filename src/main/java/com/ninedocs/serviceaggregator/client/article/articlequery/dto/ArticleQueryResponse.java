package com.ninedocs.serviceaggregator.client.article.articlequery.dto;

import lombok.Getter;

@Getter
public class ArticleQueryResponse {

  private Long id;
  private String title;
  private String contents;
  private String link;
  private Long categoryId;
}
