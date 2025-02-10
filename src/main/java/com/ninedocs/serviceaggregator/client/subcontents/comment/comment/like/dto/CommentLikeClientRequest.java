package com.ninedocs.serviceaggregator.client.subcontents.comment.comment.like.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentLikeClientRequest {

  private Long commentId;
  private Long userId;
}
