package com.ninedocs.serviceaggregator.client.subcontents.comment.reply.create.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReplyCreateClientRequest {

  private Long commentId;
  private Long userId;
  private String content;
}
