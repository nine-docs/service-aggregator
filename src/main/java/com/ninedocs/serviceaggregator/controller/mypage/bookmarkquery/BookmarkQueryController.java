package com.ninedocs.serviceaggregator.controller.mypage.bookmarkquery;

import com.ninedocs.serviceaggregator.application.auth.JwtDecoder;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import com.ninedocs.serviceaggregator.controller.common.response.CursorPageResponse;
import com.ninedocs.serviceaggregator.controller.mypage.bookmarkquery.dto.BookmarkSummaryResponse;
import com.ninedocs.serviceaggregator.controller.mypage.bookmarkquery.dto.BookmarkSummaryResponse.AuthorResponse;
import com.ninedocs.serviceaggregator.controller.mypage.bookmarkquery.dto.BookmarkSummaryResponse.ReplyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "북마크 목록 조회")
@RestController
@RequiredArgsConstructor
public class BookmarkQueryController {

  private final JwtDecoder jwtDecoder;

  @Operation(summary = "북마크 목록 조회 페이지네이션")
  @GetMapping("/api/v1/my-page/bookmarks")
  public Mono<ResponseEntity<ApiResponse<CursorPageResponse<BookmarkSummaryResponse, Long>>>> getBookmarks(
      @RequestHeader("Authentication") String authToken,
      @RequestParam(required = false) @Parameter(description = "직전 페이지의 마지막 북마크 id (첫 페이지 조회 시 null)") Long cursor,
      @RequestParam(defaultValue = "20") @Parameter(description = "한 페이지 최대 사이즈") int limit
  ) {
    return Mono.just(ResponseEntity.ok(ApiResponse.success(
        CursorPageResponse.of(
            List.of(
                BookmarkSummaryResponse.builder()
                    .commentId(1L)
                    .author(AuthorResponse.builder()
                        .id(1L)
                        .nickname("*** 테스트 댓글 작성자명 1 ***")
                        .build())
                    .reply(ReplyResponse.builder().count(0L).build())
                    .content("댓글 내용 댓글 내용 댓글 내용 댓글 내용 댓글 내용 댓글 내용 "
                        + "댓글 내용 댓글 내용 댓글 내용 댓글 내용 댓글 내용 댓글 내용 댓글 내용 댓글 내용 "
                        + "댓글 내용 댓글 내용 댓글 내용 댓글 내용 댓글 내용 댓글 내용 댓글 내용 댓글 내용 "
                        + "댓글 내용 댓글 내용 댓글 내용 댓글 내용 댓글 내용")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build()
            ),
            1L
        )
    )));
  }
}
