package com.ninedocs.serviceaggregator.client.article.allcategories.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class CategoryResult {

  private Long id;
  private String title;
}
