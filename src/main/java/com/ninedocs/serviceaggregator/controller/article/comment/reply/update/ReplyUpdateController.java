package com.ninedocs.serviceaggregator.controller.article.comment.reply.update;

import com.ninedocs.serviceaggregator.application.auth.JwtDecoder;
import com.ninedocs.serviceaggregator.client.subcontents.comment.reply.update.ReplyUpdateClient;
import com.ninedocs.serviceaggregator.client.subcontents.comment.reply.update.dto.ReplyUpdateClientRequest;
import com.ninedocs.serviceaggregator.controller.article.comment.reply.update.dto.ReplyUpdateRequest;
import com.ninedocs.serviceaggregator.controller.article.comment.reply.update.dto.ReplyUpdateResponse;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "대댓글")
@RestController
@RequiredArgsConstructor
public class ReplyUpdateController {

  private final JwtDecoder jwtDecoder;
  private final ReplyUpdateClient replyUpdateClient;

  @Operation(summary = "대댓글 수정")
  @PutMapping("/api/v1/article/{articleId}/comment/{commentId}/reply/{replyId}")
  public Mono<ResponseEntity<ApiResponse<ReplyUpdateResponse>>> updateReply(
      @PathVariable Long articleId,
      @PathVariable Long commentId,
      @PathVariable Long replyId,
      @Valid @RequestBody ReplyUpdateRequest request,
      @RequestHeader("Authentication") String authToken
  ) {
    Long userId = jwtDecoder.decode(authToken).getUserId();

    return replyUpdateClient.updateReply(
        ReplyUpdateClientRequest.builder()
            .replyId(replyId)
            .userId(userId)
            .content(request.getContent())
            .build()
    ).map(replyUpdateResponse ->
        ResponseEntity.ok(ApiResponse.success(
            ReplyUpdateResponse.builder()
                .replyId(replyUpdateResponse.getReplyId())
                .content(replyUpdateResponse.getContent())
                .createdAt(replyUpdateResponse.getCreatedAt())
                .updatedAt(replyUpdateResponse.getUpdatedAt())
                .build()
        )));
  }
}
