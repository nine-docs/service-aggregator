package com.ninedocs.serviceaggregator.controller.mypage.bookmarkquery;

import com.ninedocs.serviceaggregator.application.auth.JwtDecoder;
import com.ninedocs.serviceaggregator.client.article.articlesummariesquery.ArticleSummariesQueryClient;
import com.ninedocs.serviceaggregator.client.article.articlesummariesquery.dto.ArticleSummariesDto;
import com.ninedocs.serviceaggregator.client.subcontents.bookmark.BookmarkListQueryClient;
import com.ninedocs.serviceaggregator.client.subcontents.bookmark.dto.BookmarkSummaryResponse;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import com.ninedocs.serviceaggregator.controller.common.response.CursorPageResponse;
import com.ninedocs.serviceaggregator.controller.mypage.bookmarkquery.dto.BookmarkedArticleResponse;
import com.ninedocs.serviceaggregator.controller.mypage.bookmarkquery.dto.BookmarkedArticleResponse.ArticleDto;
import com.ninedocs.serviceaggregator.controller.mypage.bookmarkquery.dto.BookmarkedArticleResponse.CategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@Tag(name = "북마크 목록 조회")
@RestController
@RequiredArgsConstructor
public class BookmarkListQueryController {

  private final JwtDecoder jwtDecoder;
  private final BookmarkListQueryClient bookmarkListQueryClient;
  private final ArticleSummariesQueryClient articleQueryClient;

  @Operation(summary = "북마크 목록 조회 페이지네이션")
  @GetMapping("/api/v1/my-page/bookmarks")
  public Mono<ResponseEntity<ApiResponse<CursorPageResponse<BookmarkedArticleResponse, Long>>>> getBookmarks(
      @RequestHeader("Authentication") String authToken,
      @RequestParam(required = false) @Parameter(description = "직전 페이지의 마지막 북마크 id (첫 페이지 조회 시 null)") Long cursor,
      @RequestParam(defaultValue = "20") @Parameter(description = "한 페이지 최대 사이즈") int limit
  ) {
    Long userId = jwtDecoder.decode(authToken).getUserId();

    return bookmarkListQueryClient.getBookmarks(userId)    // 모든 북마크 조회
        // 필요한 북마크만 필터링
        .map(allBookmarks -> filterBookmarks(allBookmarks, cursor, limit))
        // article 조회하여 응답 완성
        .flatMap(bookmarks ->
            articleQueryClient.getArticleSummaries(
                    bookmarks.stream()
                        .map(BookmarkSummaryResponse::getArticleId)
                        .toList()
                )
                .map(articles -> toBookmarkedArticles(bookmarks, articles))
        )
        .map(bookmarkedArticles -> ResponseEntity.ok(ApiResponse.success(
                CursorPageResponse.of(
                    bookmarkedArticles,
                    CollectionUtils.isEmpty(bookmarkedArticles)
                        ? null
                        : bookmarkedArticles.get(bookmarkedArticles.size() - 1).getBookmarkId()
                )
            ))
        );
  }

  /**
   * 사용자의 모든 북마크로부터
   * 응답할 페이지에 속하는 북마크를 필터링
   *
   * @param allBookmarks 모든 북마크 목록
   * @param cursor       이전 페이지 마지막 북마크 id
   * @param limit        페이지 size
   */
  private List<BookmarkSummaryResponse> filterBookmarks(
      List<BookmarkSummaryResponse> allBookmarks, Long cursor, int limit
  ) {
    List<Long> allBookmarkIds = allBookmarks.stream()
        .map(BookmarkSummaryResponse::getId)
        .toList();

    int startIndex = 0;
    if (cursor != null) {
      for (int i = 0; i < allBookmarkIds.size(); i++) {
        if (allBookmarkIds.get(i).equals(cursor)) {
          startIndex = i + 1;
          break;
        }
      }
    }
    if (startIndex > allBookmarks.size() - 1) {
      return Collections.emptyList();
    }
    int lastIndex = Math.min((startIndex + limit), allBookmarks.size());

    return allBookmarks.subList(startIndex, lastIndex);
  }

  /**
   * bookmarks 및 articles 조회 결과를 가지고
   * 페이지네이션 items 생성
   */
  private List<BookmarkedArticleResponse> toBookmarkedArticles(
      List<BookmarkSummaryResponse> bookmarks, ArticleSummariesDto articleSummaries
  ) {
    return bookmarks.stream()
        .filter(bookmark -> articleSummaries.isArticleExist(bookmark.getArticleId()))
        .map(bookmark -> BookmarkedArticleResponse.builder()
            .bookmarkId(bookmark.getId())
            .article(ArticleDto.builder()
                .id(bookmark.getArticleId())
                .title(articleSummaries.get(bookmark.getArticleId()).getTitle())
                .category(CategoryDto.builder()
                    .id(articleSummaries.get(bookmark.getArticleId()).getCategory().getId())
                    .name(articleSummaries.get(bookmark.getArticleId()).getCategory().getName())
                    .build())
                .build())
            .build())
        .toList();
  }
}
