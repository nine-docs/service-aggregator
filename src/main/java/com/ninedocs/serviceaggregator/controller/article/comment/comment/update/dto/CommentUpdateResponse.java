package com.ninedocs.serviceaggregator.controller.article.comment.comment.update.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentUpdateResponse {

  private Long commentId;
  private String content;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
