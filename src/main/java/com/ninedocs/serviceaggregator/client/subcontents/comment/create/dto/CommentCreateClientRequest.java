package com.ninedocs.serviceaggregator.client.subcontents.comment.create.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentCreateClientRequest {

  private Long articleId;
  private Long userId;
  private String content;
}
