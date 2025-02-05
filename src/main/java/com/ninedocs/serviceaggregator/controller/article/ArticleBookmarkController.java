package com.ninedocs.serviceaggregator.controller.article;

import com.ninedocs.serviceaggregator.application.auth.JwtDecoder;
import com.ninedocs.serviceaggregator.client.subcontents.bookmark.BookmarkQueryClient;
import com.ninedocs.serviceaggregator.controller.article.dto.BookmarkResponse;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@Tag(name = "문제페이지")
@RestController
@RequiredArgsConstructor
public class ArticleBookmarkController {

  private final JwtDecoder jwtDecoder;
  private final BookmarkQueryClient bookmarkQueryClient;

  @Operation(summary = "특정 문제의 북마크 여부 조회")
  @GetMapping("/api/v1/article/{articleId}/bookmark")
  public Mono<ResponseEntity<ApiResponse<BookmarkResponse>>> getArticleBookmarkExist(
      @PathVariable @Parameter(example = "1") Long articleId,
      @RequestHeader("Authentication") String authToken
  ) {
    try {
      Long userId = jwtDecoder.decode(authToken).getUserId();

      return bookmarkQueryClient.getArticleBookmarkExist(userId, articleId)
          .map(bookmarkOptional -> bookmarkOptional.map(bookmarkIdResponse ->
              new BookmarkResponse(bookmarkIdResponse.getId())
          ))
          .map(bookmarkResponseOptional -> ResponseEntity.ok(ApiResponse.success(
              bookmarkResponseOptional.orElse(null)
          )));

    } catch (ExpiredJwtException e) {
      log.warn("# 만료된 토큰으로 요청 발생 - /api/v1/article/{}/bookmark", articleId);
      return Mono.just(ResponseEntity.ok(ApiResponse.success(null)));
    }
  }
}
