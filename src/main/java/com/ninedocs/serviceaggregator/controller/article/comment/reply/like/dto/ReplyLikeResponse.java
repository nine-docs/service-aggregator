package com.ninedocs.serviceaggregator.controller.article.comment.reply.like.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReplyLikeResponse {

  private Long replyId;
  private LikeResponse like;

  @Getter
  @Builder
  public static class LikeResponse {

    private int count;
    private Boolean isUserLike;
  }
}
