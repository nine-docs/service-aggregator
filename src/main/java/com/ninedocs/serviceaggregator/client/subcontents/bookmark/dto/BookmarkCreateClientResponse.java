package com.ninedocs.serviceaggregator.client.subcontents.bookmark.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookmarkCreateClientResponse {

  private Long bookmarkId;
  private Long userId;
  private Long articleId;
  private LocalDateTime createdAt;
}
