package com.ninedocs.serviceaggregator.controller.article.comment.reply.common.dto;

import com.ninedocs.serviceaggregator.controller.article.comment.common.AuthorResponse;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReplyResponse {

  private Long replyId;
  private AuthorResponse author;
  private String content;
  private LikeResponse like;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @Builder
  @Getter
  public static class LikeResponse {

    private int count;
    private Boolean isUserLike;
  }
}
