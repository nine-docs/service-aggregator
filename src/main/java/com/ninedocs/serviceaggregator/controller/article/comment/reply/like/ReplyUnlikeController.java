package com.ninedocs.serviceaggregator.controller.article.comment.reply.like;

import com.ninedocs.serviceaggregator.application.auth.JwtDecoder;
import com.ninedocs.serviceaggregator.client.subcontents.comment.reply.like.ReplyUnlikeClient;
import com.ninedocs.serviceaggregator.client.subcontents.comment.reply.like.dto.ReplyLikeClientRequest;
import com.ninedocs.serviceaggregator.controller.article.comment.reply.like.dto.ReplyLikeResponse;
import com.ninedocs.serviceaggregator.controller.article.comment.reply.like.dto.ReplyLikeResponse.LikeResponse;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@Tag(name = "댓글/대댓글")
@RestController
@RequiredArgsConstructor
public class ReplyUnlikeController {

  private final JwtDecoder jwtDecoder;
  private final ReplyUnlikeClient replyUnlikeClient;

  @Operation(summary = "대댓글 좋아요 취소")
  @DeleteMapping("/api/v1/article/{articleId}/comment/{commentId}/reply/{replyId}/like")
  public Mono<ResponseEntity<ApiResponse<ReplyLikeResponse>>> unlikeReply(
      @PathVariable Long articleId,
      @PathVariable Long commentId,
      @PathVariable Long replyId,
      @RequestHeader("Authentication") String authToken
  ) {
    Long userId = jwtDecoder.decode(authToken).getUserId();

    return replyUnlikeClient.unlikeReply(
            ReplyLikeClientRequest.builder()
                .replyId(replyId)
                .userId(userId)
                .build()
        )
        .map(reply -> ResponseEntity.ok(ApiResponse.success(
            ReplyLikeResponse.builder()
                .replyId(reply.getReplyId())
                .like(LikeResponse.builder()
                    .count(reply.getLike().getCount())
                    .isUserLike(reply.getLike().getIsUserLike())
                    .build())
                .build()
        )));
  }
}
