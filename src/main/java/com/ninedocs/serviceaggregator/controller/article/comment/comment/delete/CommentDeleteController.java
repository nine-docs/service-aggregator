package com.ninedocs.serviceaggregator.controller.article.comment.comment.delete;

import com.ninedocs.serviceaggregator.application.auth.JwtDecoder;
import com.ninedocs.serviceaggregator.client.subcontents.comment.comment.delete.CommentDeleteClient;
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

@Tag(name = "댓글")
@RestController
@RequiredArgsConstructor
public class CommentDeleteController {

  private final JwtDecoder jwtDecoder;
  private final CommentDeleteClient commentDeleteClient;

  @DeleteMapping("/api/v1/article/{articleId}/comment/{commentId}")
  @Operation(summary = "댓글 삭제")
  public Mono<ResponseEntity<ApiResponse<Void>>> deleteComment(
      @PathVariable Long articleId,
      @PathVariable Long commentId,
      @RequestHeader("Authentication") String authToken
  ) {
    Long userId = jwtDecoder.decode(authToken).getUserId();
    return commentDeleteClient.deleteComment(commentId, userId)
        .thenReturn(ResponseEntity.ok(ApiResponse.success()));
  }
}
