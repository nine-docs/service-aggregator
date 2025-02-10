package com.ninedocs.serviceaggregator.client.subcontents.comment.reply.update.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ReplyUpdateClientResponse {

  private Long replyId;
  private String content;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
