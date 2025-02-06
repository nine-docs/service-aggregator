package com.ninedocs.serviceaggregator.client.subcontents.comment.update.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentUpdateClientRequest {

  private Long commentId;
  private Long userId;
  private String content;
}
