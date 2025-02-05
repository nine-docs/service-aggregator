package com.ninedocs.serviceaggregator.controller.mypage.bookmarkquery.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookmarkArticleResponse {

  private Long bookmarkId;
  private ArticleDto article;


  @Getter
  @Builder
  public static class ArticleDto {

    private Long id;
    private String title;
    private CategoryDto category;
  }

  @Getter
  @Builder
  public static class CategoryDto {

    private Long id;
    private String name;
  }
}
