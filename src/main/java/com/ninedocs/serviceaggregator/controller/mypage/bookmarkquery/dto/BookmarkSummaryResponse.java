package com.ninedocs.serviceaggregator.controller.mypage.bookmarkquery.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookmarkSummaryResponse {

  private Long commentId;
  private AuthorResponse author;
  private ReplyResponse reply;
  private String content;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @Getter
  @Builder
  public static class AuthorResponse {

    private Long id;
    private String nickname;
  }

  @Getter
  @Builder
  public static class ReplyResponse {

    private Long count;
  }
}
