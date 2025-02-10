package com.ninedocs.serviceaggregator.client.subcontents.comment.reply.query.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class ReplyCursorResponse {

  private Long cursor;
  private List<ReplyClientResponse> items;

  @Getter
  public static class ReplyClientResponse {

    private Long replyId;
    private Long authorId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
  }
}
