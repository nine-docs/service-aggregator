package com.ninedocs.serviceaggregator.controller.article.comment.update;

import com.ninedocs.serviceaggregator.application.auth.JwtDecoder;
import com.ninedocs.serviceaggregator.client.subcontents.comment.update.CommentUpdateClient;
import com.ninedocs.serviceaggregator.client.subcontents.comment.update.dto.CommentUpdateClientRequest;
import com.ninedocs.serviceaggregator.controller.article.comment.update.dto.CommentUpdateRequest;
import com.ninedocs.serviceaggregator.controller.article.comment.update.dto.CommentUpdateResponse;
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

@Tag(name = "댓글/대댓글")
@RestController
@RequiredArgsConstructor
public class CommentUpdateController {

  private final JwtDecoder jwtDecoder;
  private final CommentUpdateClient commentUpdateClient;

  @Operation(summary = "댓글 내용 수정")
  @PutMapping("/api/v1/article/{articleId}/comment/{commentId}")
  public Mono<ResponseEntity<ApiResponse<CommentUpdateResponse>>> updateComment(
      @PathVariable Long articleId,
      @PathVariable Long commentId,
      @Valid @RequestBody CommentUpdateRequest request,
      @RequestHeader("Authentication") String authToken
  ) {
    Long userId = jwtDecoder.decode(authToken).getUserId();

    return commentUpdateClient.updateComment(
            CommentUpdateClientRequest.builder()
                .userId(userId)
                .commentId(commentId)
                .content(request.getContent())
                .build()
        )
        .map(clientResponse ->
            ResponseEntity.ok(ApiResponse.success(
                CommentUpdateResponse.builder()
                    .commentId(clientResponse.getCommentId())
                    .content(clientResponse.getContent())
                    .createdAt(clientResponse.getCreatedAt())
                    .updatedAt(clientResponse.getUpdatedAt())
                    .build()
            )));
  }
}
