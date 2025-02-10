package com.ninedocs.serviceaggregator.controller.article.comment.reply.create.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ReplyCreateRequest {

  @NotNull
  @Size(max = 10_000, min = 1)
  private String content;
}
