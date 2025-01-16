package com.ninedocs.serviceaggregator.client.article.usercategoryquery.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class UserCategoryQueryResponse {

  private Long userId;
  private List<String> categoryTitles;
}
