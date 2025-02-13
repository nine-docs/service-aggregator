package com.ninedocs.serviceaggregator.client.subcontents.comment.reply.like.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReplyLikeClientRequest {

  private Long replyId;
  private Long userId;
}
