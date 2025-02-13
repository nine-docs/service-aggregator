package com.ninedocs.serviceaggregator.client.subcontents.comment.reply.like.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplyLikeClientResponse {

  private Long replyId;
  private Long authorId;
  private String content;
  private LikeResponse like;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @Getter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class LikeResponse {

    private int count;
    private Boolean isUserLike;
  }
}
