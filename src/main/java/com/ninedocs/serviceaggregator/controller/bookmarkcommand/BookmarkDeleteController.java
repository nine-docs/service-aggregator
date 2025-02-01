package com.ninedocs.serviceaggregator.controller.bookmarkcommand;

import com.ninedocs.serviceaggregator.application.auth.JwtDecoder;
import com.ninedocs.serviceaggregator.client.subcontents.bookmark.BookmarkDeleteClient;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "북마크 생성/삭제")
@RequiredArgsConstructor
@RestController
public class BookmarkDeleteController {

  private final JwtDecoder jwtDecoder;
  private final BookmarkDeleteClient bookmarkDeleteClient;

  @DeleteMapping("/api/v1/bookmark/{bookmarkId}")
  @Operation(summary = "북마크 해제하기")
  public Mono<ResponseEntity<ApiResponse<Void>>> createBookmark(
      @RequestHeader("Authentication") String authToken,
      @PathVariable Long bookmarkId
  ) {
    Long userId = jwtDecoder.decode(authToken).getUserId();

    return bookmarkDeleteClient.deleteBookmark(bookmarkId, userId)
        .thenReturn(ResponseEntity.ok(ApiResponse.success()));
  }
}
