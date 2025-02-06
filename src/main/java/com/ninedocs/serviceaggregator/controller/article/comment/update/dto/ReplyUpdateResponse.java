package com.ninedocs.serviceaggregator.controller.article.comment.update.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReplyUpdateResponse {

  private Long replyId;
  private String content;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
