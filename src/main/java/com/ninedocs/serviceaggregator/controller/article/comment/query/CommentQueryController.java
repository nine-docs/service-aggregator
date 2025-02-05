package com.ninedocs.serviceaggregator.controller.article.comment.query;

import com.ninedocs.serviceaggregator.controller.article.comment.common.dto.CommentResponse;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import com.ninedocs.serviceaggregator.controller.common.response.CursorPageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "댓글/대댓글")
@RestController
public class CommentQueryController {

  @Operation(summary = "댓글 목록 조회 페이지네이션")
  @GetMapping("/api/v1/article/{articleId}/comments")
  public Mono<ResponseEntity<ApiResponse<CursorPageResponse<CommentResponse, Long>>>> getComments(
      @PathVariable Long articleId,
      @Parameter(description = "이전 페이지 마지막 댓글 (첫페이지 조회 시 null)") @RequestParam(required = false) Long cursor,
      @Parameter(description = "페이지 당 아이템 최대 갯수") @RequestParam(defaultValue = "20") int limit,
      @Parameter(description = "내 댓글 여부 계산 용 토큰, 생략 가능") @RequestHeader(value = "Authentication", required = false) String authToken
  ) {
    return Mono.just(ResponseEntity.ok(ApiResponse.success(
        CursorPageResponse.of(List.of(CommentResponse.builder().build()), null)
    )));
  }
}
