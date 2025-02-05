package com.ninedocs.serviceaggregator.controller.mypage.bookmarkquery;

import com.ninedocs.serviceaggregator.application.auth.JwtDecoder;
import com.ninedocs.serviceaggregator.client.article.articlesummariesquery.ArticleSummariesQueryClient;
import com.ninedocs.serviceaggregator.client.article.articlesummariesquery.dto.ArticleSummaryResponse;
import com.ninedocs.serviceaggregator.client.subcontents.bookmark.BookmarkListQueryClient;
import com.ninedocs.serviceaggregator.client.subcontents.bookmark.dto.BookmarkSummaryResponse;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import com.ninedocs.serviceaggregator.controller.common.response.CursorPageResponse;
import com.ninedocs.serviceaggregator.controller.mypage.bookmarkquery.dto.BookmarkArticleResponse;
import com.ninedocs.serviceaggregator.controller.mypage.bookmarkquery.dto.BookmarkArticleResponse.ArticleDto;
import com.ninedocs.serviceaggregator.controller.mypage.bookmarkquery.dto.BookmarkArticleResponse.CategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
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
  public Mono<ResponseEntity<ApiResponse<CursorPageResponse<BookmarkArticleResponse, Long>>>> getBookmarks(
      @RequestHeader("Authentication") String authToken,
      @RequestParam(required = false) @Parameter(description = "직전 페이지의 마지막 북마크 id (첫 페이지 조회 시 null)") Long cursor,
      @RequestParam(defaultValue = "20") @Parameter(description = "한 페이지 최대 사이즈") int limit
  ) {
    Long userId = jwtDecoder.decode(authToken).getUserId();

    return bookmarkListQueryClient.getBookmarks(userId)
        .map((Function<List<BookmarkSummaryResponse>, List<BookmarkSummaryResponse>>)
            allBookmarks -> {
              List<Long> allBookmarkIds = allBookmarks.stream()
                  .map(BookmarkSummaryResponse::getId)
                  .toList();

              log.debug("# bookmark IDs : {}", allBookmarkIds);

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

              log.debug("# subList({}, {})", startIndex, lastIndex);

              return allBookmarks.subList(startIndex, lastIndex);
            })
        .flatMap(bookmarks -> articleQueryClient.getArticleSummaries(
                bookmarks.stream()
                    .map(BookmarkSummaryResponse::getArticleId)
                    .toList()
            )
            .map(articleSummaries ->
                bookmarks.stream()
                    .filter(bookmark ->
                        articleSummaries.isArticleExist(bookmark.getArticleId())
                    )
                    .map(bookmark -> {
                      ArticleSummaryResponse article =
                          articleSummaries.getArticleSummary(bookmark.getArticleId());

                      return BookmarkArticleResponse.builder()
                          .bookmarkId(bookmark.getId())
                          .article(ArticleDto.builder()
                              .id(bookmark.getArticleId())
                              .title(article.getTitle())
                              .category(CategoryDto.builder()
                                  .id(article.getCategory().getId())
                                  .name(article.getCategory().getName())
                                  .build())
                              .build())
                          .build();
                    })
                    .toList()
            )
            .map(bookmarkedArticles -> {
              Long nextCursor = CollectionUtils.isEmpty(bookmarkedArticles)
                  ? null
                  : bookmarkedArticles.get(bookmarkedArticles.size() - 1).getBookmarkId();

              return ResponseEntity.ok(ApiResponse.success(
                  CursorPageResponse.of(bookmarkedArticles, nextCursor)
              ));
            }));
  }
}
