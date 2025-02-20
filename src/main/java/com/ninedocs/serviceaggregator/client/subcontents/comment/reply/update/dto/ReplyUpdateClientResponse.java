package com.ninedocs.serviceaggregator.client.subcontents.comment.reply.update.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ReplyUpdateClientResponse {

  private Long replyId;
  private Long authorId;
  private String content;
  private LikeResponse like;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @Getter
  public static class LikeResponse {

    private Integer count;
    private Boolean isUserLike;
  }
}
