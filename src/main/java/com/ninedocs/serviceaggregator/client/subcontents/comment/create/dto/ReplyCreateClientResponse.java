package com.ninedocs.serviceaggregator.client.subcontents.comment.create.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplyCreateClientResponse {

  private Long replyId;
  private Long commentId;
  private String content;
  private LocalDateTime createdAt;
}
