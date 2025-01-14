package com.ninedocs.serviceaggregator.client.article.usercategoryupsert.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCategoryUpsertResponse {

  private Long id;
  private String userEmail;
  private Long categoryId;
  private String categoryTitle;
  private Boolean isActivated;
}
