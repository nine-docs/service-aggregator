package com.ninedocs.serviceaggregator.client.subcontents.comment.comment.query.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentQueryRequest {

  private Long articleId;
  private Long cursor;
  private int limit;
  // 댓글 별 자신의 좋아요 여부를 응답받기 위함 (비로그인 시 null)
  private Long userId;
}
