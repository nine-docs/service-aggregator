package com.ninedocs.serviceaggregator.controller.article.comment.comment.common.dto;

import com.ninedocs.serviceaggregator.controller.article.comment.common.AuthorResponse;
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
  private LikeResponse like;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime deletedAt;

  @Builder
  @Getter
  public static class ReplyResponse {

    private int count;
  }

  @Builder
  @Getter
  public static class LikeResponse {

    private Long count;
    private Boolean isUserLike;
  }
}
