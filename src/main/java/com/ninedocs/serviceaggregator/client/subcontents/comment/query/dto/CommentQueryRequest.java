package com.ninedocs.serviceaggregator.client.subcontents.comment.query.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentQueryRequest {

  private Long articleId;
  private Long cursor;
  private int limit;
}
