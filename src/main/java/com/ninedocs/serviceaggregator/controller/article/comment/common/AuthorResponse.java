package com.ninedocs.serviceaggregator.controller.article.comment.common;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthorResponse {

  private Long id;
  private String nickname;
  private Boolean isMe;
}