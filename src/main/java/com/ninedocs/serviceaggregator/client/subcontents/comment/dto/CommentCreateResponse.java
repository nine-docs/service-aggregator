package com.ninedocs.serviceaggregator.client.subcontents.comment.dto;

import lombok.Getter;

@Getter
public class CommentCreateResponse {

  private Long commentId;
  private String content;
  // Todo subcontents 변경되면 LocalDateTime 으로 변경
  private String createdAt;
}
