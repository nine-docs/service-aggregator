package com.ninedocs.serviceaggregator.controller.bookmarkcommand;

import com.ninedocs.serviceaggregator.application.auth.JwtDecoder;
import com.ninedocs.serviceaggregator.client.subcontents.bookmark.BookmarkCreateClient;
import com.ninedocs.serviceaggregator.controller.bookmarkcommand.request.BookmarkCreateRequest;
import com.ninedocs.serviceaggregator.controller.bookmarkcommand.response.BookmarkCreateResponse;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "북마크 생성/삭제")
@RequiredArgsConstructor
@RestController
public class BookmarkCreateController {

  private final JwtDecoder jwtDecoder;
  private final BookmarkCreateClient bookmarkCreateClient;

  @Operation(summary = "북마크하기")
  @PostMapping("/api/v1/bookmark")
  public Mono<ResponseEntity<ApiResponse<BookmarkCreateResponse>>> createBookmark(
      @RequestHeader("Authentication") String authToken,
      @RequestBody @Valid BookmarkCreateRequest request
  ) {
    Long userId = jwtDecoder.decode(authToken).getUserId();

    return bookmarkCreateClient.createBookmark(userId, request.getArticleId())
        .map(response ->
            ResponseEntity.status(HttpStatus.CREATED.value()).body(ApiResponse.success(
                BookmarkCreateResponse.builder()
                    .bookmarkId(response.getBookmarkId())
                    .articleId(request.getArticleId())
                    .build()
            )));
  }
}
