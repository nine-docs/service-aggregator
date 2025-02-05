package com.ninedocs.serviceaggregator.client.subcontents.bookmark.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookmarkCreateClientRequest {

  private Long articleId;
  private Long userId;
}
