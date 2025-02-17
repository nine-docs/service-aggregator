package com.ninedocs.serviceaggregator.client.subcontents.comment.comment.query.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class CommentCursorResponse {

  private Long cursor;
  private List<CommentClientResponse> items;
}
