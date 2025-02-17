package com.ninedocs.serviceaggregator.client.subcontents.comment.comment.query.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BestCommentRequest {

  private Long articleId;
  private Long userId;
  private int limit;
}
