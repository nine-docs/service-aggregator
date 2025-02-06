package com.ninedocs.serviceaggregator.controller.article.comment.common.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReplyResponse {

  private Long replyId;
  private AuthorResponse author;
  private String content;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
