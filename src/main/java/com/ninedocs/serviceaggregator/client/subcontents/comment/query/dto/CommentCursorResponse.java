package com.ninedocs.serviceaggregator.client.subcontents.comment.query.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class CommentCursorResponse {

  private Long cursor;
  private List<CommentClientResponse> items;

  @Getter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class CommentClientResponse {

    private Long commentId;
    private Long authorId;
    private ReplyResponse reply;
    private String content;
    private LikeResponse like;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ReplyResponse {

      private int count;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LikeResponse {

      private Long count;
      private Boolean isUserLike;
    }
  }
}
