package com.ninedocs.serviceaggregator.client.subcontents.comment.comment.like.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentLikeClientResponse {

  private Long commentId;
  private Long authorId;
  private String content;
  private ReplyResponse reply;
  private LikeResponse like;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime deletedAt;

  @Getter
  public static class ReplyResponse {

    private int count;
  }

  @Getter
  public static class LikeResponse {

    private int count;
    private Boolean isUserLike;
  }
}
