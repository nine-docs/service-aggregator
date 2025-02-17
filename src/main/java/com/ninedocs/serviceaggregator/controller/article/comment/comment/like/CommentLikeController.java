package com.ninedocs.serviceaggregator.controller.article.comment.comment.like;

import com.ninedocs.serviceaggregator.application.auth.JwtDecoder;
import com.ninedocs.serviceaggregator.client.subcontents.comment.comment.like.CommentLikeClient;
import com.ninedocs.serviceaggregator.client.subcontents.comment.comment.like.dto.CommentLikeClientRequest;
import com.ninedocs.serviceaggregator.controller.article.comment.comment.like.dto.CommentLikeResponse;
import com.ninedocs.serviceaggregator.controller.article.comment.comment.like.dto.CommentLikeResponse.LikeResponse;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "댓글")
@RestController
@RequiredArgsConstructor
public class CommentLikeController {

  private final JwtDecoder jwtDecoder;
  private final CommentLikeClient commentLikeClient;

  @Operation(summary = "댓글 좋아요")
  @PostMapping("/api/v1/article/{articleId}/comment/{commentId}/like")
  public Mono<ResponseEntity<ApiResponse<CommentLikeResponse>>> likeComment(
      @PathVariable Long articleId,
      @PathVariable Long commentId,
      @RequestHeader("Authentication") String authToken
  ) {
    Long userId = jwtDecoder.decode(authToken).getUserId();

    return commentLikeClient.like(
            CommentLikeClientRequest.builder()
                .commentId(commentId)
                .userId(userId)
                .build()
        )
        .map(comment -> ResponseEntity.ok(ApiResponse.success(
            CommentLikeResponse.builder()
                .commentId(comment.getCommentId())
                .like(LikeResponse.builder()
                    .count(comment.getLike().getCount())
                    .isUserLike(comment.getLike().getIsUserLike())
                    .build())
                .build()
        )));
  }
}
