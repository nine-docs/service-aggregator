package com.ninedocs.serviceaggregator.client.subcontents.comment.reply.query.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class ReplyCursorResponse {

  private Long cursor;
  private List<ReplyClientResponse> items;

  @Getter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ReplyClientResponse {

    private Long replyId;
    private Long authorId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
  }
}
