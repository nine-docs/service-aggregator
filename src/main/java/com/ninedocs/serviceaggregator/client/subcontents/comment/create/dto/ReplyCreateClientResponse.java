package com.ninedocs.serviceaggregator.client.subcontents.comment.create.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ReplyCreateClientResponse {

  private Long replyId;
  private Long commentId;
  private String content;
  private LocalDateTime createdAt;
}
