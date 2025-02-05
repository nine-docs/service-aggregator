package com.ninedocs.serviceaggregator.controller.article.comment.create;

import com.ninedocs.serviceaggregator.application.auth.JwtDecoder;
import com.ninedocs.serviceaggregator.client.subcontents.comment.CommentCreateClient;
import com.ninedocs.serviceaggregator.client.subcontents.comment.dto.CommentCreateClientRequest;
import com.ninedocs.serviceaggregator.client.user.profile.UserProfileClient;
import com.ninedocs.serviceaggregator.controller.article.comment.common.dto.CommentResponse;
import com.ninedocs.serviceaggregator.controller.article.comment.common.dto.CommentResponse.AuthorResponse;
import com.ninedocs.serviceaggregator.controller.article.comment.common.dto.CommentResponse.ReplyResponse;
import com.ninedocs.serviceaggregator.controller.article.comment.create.dto.CommentCreateRequest;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
public class CommentCreateController {

  private final JwtDecoder jwtDecoder;
  private final CommentCreateClient commentCreateClient;
  private final UserProfileClient userProfileClient;

  @Operation(summary = "댓글 작성")
  @PostMapping("/api/v1/article/{articleId}/comment")
  public Mono<ResponseEntity<ApiResponse<CommentResponse>>> create(
      @PathVariable Long articleId,
      @RequestHeader("Authentication") String authToken,
      @RequestBody CommentCreateRequest request
  ) {
    Long userId = jwtDecoder.decode(authToken).getUserId();

    return Mono.zip(
        commentCreateClient.createComment(
            CommentCreateClientRequest.builder()
                .userId(userId)
                .articleId(articleId)
                .content(request.getContent())
                .build()
        ),
        userProfileClient.userProfile(userId),
        (commentCreateResponse, userProfile) ->
            ResponseEntity.status(HttpStatus.CREATED.value()).body(ApiResponse.success(
                CommentResponse.builder()
                    .commentId(commentCreateResponse.getCommentId())
                    .author(AuthorResponse.builder()
                        .id(userId)
                        .nickname(userProfile.getNickname())
                        .isMe(true)
                        .build())
                    .content(commentCreateResponse.getContent())
                    .reply(ReplyResponse.builder()
                        .count(0)
                        .build())
                    .createdAt(null)
                    .updatedAt(null)
                    .build()
            ))
    );
  }
}
