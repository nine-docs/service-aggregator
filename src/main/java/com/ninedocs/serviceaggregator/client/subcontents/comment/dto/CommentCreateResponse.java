package com.ninedocs.serviceaggregator.client.subcontents.comment.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentCreateResponse {

  private Long commentId;
  private String content;
  private LocalDateTime createdAt;
}
