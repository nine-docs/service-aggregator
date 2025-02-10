package com.ninedocs.serviceaggregator.controller.article.comment.create;

import com.ninedocs.serviceaggregator.application.auth.JwtDecoder;
import com.ninedocs.serviceaggregator.client.subcontents.comment.reply.create.ReplyCreateClient;
import com.ninedocs.serviceaggregator.client.subcontents.comment.reply.create.dto.ReplyCreateClientRequest;
import com.ninedocs.serviceaggregator.client.user.profile.UserProfileQueryClient;
import com.ninedocs.serviceaggregator.controller.article.comment.common.dto.AuthorResponse;
import com.ninedocs.serviceaggregator.controller.article.comment.common.dto.ReplyResponse;
import com.ninedocs.serviceaggregator.controller.article.comment.create.dto.ReplyCreateRequest;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "댓글/대댓글")
@RestController
@RequiredArgsConstructor
public class ReplyCreateController {

  private final JwtDecoder jwtDecoder;
  private final ReplyCreateClient replyCreateClient;
  private final UserProfileQueryClient userProfileQueryClient;

  @Operation(summary = "대댓글 작성")
  @PostMapping("/api/v1/article/{articleId}/comment/{commentId}/reply")
  public Mono<ResponseEntity<ApiResponse<ReplyResponse>>> createReply(
      @PathVariable Long articleId,
      @PathVariable Long commentId,
      @RequestHeader("Authentication") String authToken,
      @Valid @RequestBody ReplyCreateRequest request
  ) {
    Long userId = jwtDecoder.decode(authToken).getUserId();

    return Mono.zip(
        replyCreateClient.createReply(
            ReplyCreateClientRequest.builder()
                .userId(userId)
                .commentId(commentId)
                .content(request.getContent())
                .build()
        ),
        userProfileQueryClient.getUserProfile(userId),
        (replyCreateResponse, userProfile) ->
            ResponseEntity.status(HttpStatus.CREATED.value()).body(ApiResponse.success(
                ReplyResponse.builder()
                    .replyId(replyCreateResponse.getReplyId())
                    .author(AuthorResponse.builder()
                        .id(userId)
                        .nickname(userProfile.getNickname())
                        .isMe(true)
                        .build())
                    .content(replyCreateResponse.getContent())
                    .createdAt(replyCreateResponse.getCreatedAt())
                    .updatedAt(replyCreateResponse.getCreatedAt())
                    .build()
            ))
    );
  }
}
