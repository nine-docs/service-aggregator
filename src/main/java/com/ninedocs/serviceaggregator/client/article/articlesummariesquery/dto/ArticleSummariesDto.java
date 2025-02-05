package com.ninedocs.serviceaggregator.client.article.articlesummariesquery.dto;

import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ArticleSummariesDto {

  private final Map<Long, ArticleSummaryResponse> map;

  public static ArticleSummariesDto empty() {
    return new ArticleSummariesDto(Collections.emptyMap());
  }

  public boolean isArticleExist(Long articleId) {
    return this.map.containsKey(articleId);
  }

  public ArticleSummaryResponse getArticleSummary(Long articleId) {
    return map.get(articleId);
  }
}
