package com.ninedocs.serviceaggregator.client.subcontents.comment.reply.update.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReplyUpdateClientRequest {

  private Long replyId;
  private Long userId;
  private String content;
}
