package com.ninedocs.serviceaggregator.controller.article.comment.delete;

import com.ninedocs.serviceaggregator.application.auth.JwtDecoder;
import com.ninedocs.serviceaggregator.client.subcontents.comment.delete.ReplyDeleteClient;
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

@Tag(name = "댓글/대댓글")
@RestController
@RequiredArgsConstructor
public class ReplyDeleteController {

  private final JwtDecoder jwtDecoder;
  private final ReplyDeleteClient replyDeleteClient;

  @DeleteMapping("/api/v1/article/{articleId}/comment/{commentId}/reply/{replyId}")
  @Operation(summary = "대댓글 삭제")
  public Mono<ResponseEntity<ApiResponse<Void>>> deleteReply(
      @PathVariable Long articleId,
      @PathVariable Long commentId,
      @PathVariable Long replyId,
      @RequestHeader("Authentication") String authToken
  ) {
    Long userId = jwtDecoder.decode(authToken).getUserId();
    return replyDeleteClient.deleteReply(replyId, userId)
        .thenReturn(ResponseEntity.ok(ApiResponse.success()));
  }
}
