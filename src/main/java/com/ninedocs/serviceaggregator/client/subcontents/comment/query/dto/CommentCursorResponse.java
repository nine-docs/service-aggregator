package com.ninedocs.serviceaggregator.client.subcontents.comment.query.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class CommentCursorResponse {

  private Long cursor;
  private List<CommentClientResponse> items;

  @Getter
  public static class CommentClientResponse {

    private Long commentId;
    private Long authorId;
    private ReplyResponse reply;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Getter
    public static class ReplyResponse {

      private int count;
    }
  }
}
