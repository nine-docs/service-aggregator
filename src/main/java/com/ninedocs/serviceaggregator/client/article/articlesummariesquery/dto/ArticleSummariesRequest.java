package com.ninedocs.serviceaggregator.client.article.articlesummariesquery.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleSummariesRequest {

  private List<Long> articleIds;
}
