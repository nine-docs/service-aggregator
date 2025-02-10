package com.ninedocs.serviceaggregator.client.subcontents.comment.reply.query.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReplyQueryRequest {

  private Long commentId;
  private Long cursor;
  private int limit;
}
