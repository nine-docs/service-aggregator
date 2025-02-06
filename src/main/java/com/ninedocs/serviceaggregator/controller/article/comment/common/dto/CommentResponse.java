package com.ninedocs.serviceaggregator.controller.article.comment.common.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponse {

  private Long commentId;
  private AuthorResponse author;
  private ReplyResponse reply;
  private String content;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @Builder
  @Getter
  public static class ReplyResponse {

    private int count;
  }
}
