package com.ninedocs.serviceaggregator.client.subcontents.comment.comment.update.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentUpdateClientResponse {

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
