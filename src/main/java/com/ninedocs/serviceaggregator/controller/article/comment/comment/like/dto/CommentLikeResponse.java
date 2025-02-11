package com.ninedocs.serviceaggregator.controller.article.comment.comment.like.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentLikeResponse {

  private Long commentId;
  private LikeResponse like;

  @Getter
  @Builder
  public static class LikeResponse {

    private int count;
    private Boolean isUserLike;
  }
}
