package com.ninedocs.serviceaggregator.client.subcontents.bookmark.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookmarkSummaryResponse {

  private Long id;
  private Long articleId;
}
