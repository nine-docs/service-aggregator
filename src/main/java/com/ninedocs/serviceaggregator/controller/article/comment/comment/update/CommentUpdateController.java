package com.ninedocs.serviceaggregator.controller.article.comment.comment.update;

import com.ninedocs.serviceaggregator.application.auth.JwtDecoder;
import com.ninedocs.serviceaggregator.client.subcontents.comment.comment.update.CommentUpdateClient;
import com.ninedocs.serviceaggregator.client.subcontents.comment.comment.update.dto.CommentUpdateClientRequest;
import com.ninedocs.serviceaggregator.client.user.profile.UserProfileQueryClient;
import com.ninedocs.serviceaggregator.controller.article.comment.comment.common.dto.CommentResponse;
import com.ninedocs.serviceaggregator.controller.article.comment.comment.common.dto.CommentResponse.LikeResponse;
import com.ninedocs.serviceaggregator.controller.article.comment.comment.common.dto.CommentResponse.ReplyResponse;
import com.ninedocs.serviceaggregator.controller.article.comment.comment.update.dto.CommentUpdateRequest;
import com.ninedocs.serviceaggregator.controller.article.comment.common.AuthorResponse;
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
  private final UserProfileQueryClient userProfileQueryClient;

  @Operation(summary = "댓글 내용 수정")
  @PutMapping("/api/v1/article/{articleId}/comment/{commentId}")
  public Mono<ResponseEntity<ApiResponse<CommentResponse>>> updateComment(
      @PathVariable Long articleId,
      @PathVariable Long commentId,
      @Valid @RequestBody CommentUpdateRequest request,
      @RequestHeader("Authentication") String authToken
  ) {
    Long userId = jwtDecoder.decode(authToken).getUserId();

    return Mono.zip(
        commentUpdateClient.updateComment(CommentUpdateClientRequest.builder()
            .userId(userId)
            .commentId(commentId)
            .content(request.getContent())
            .build()),
        userProfileQueryClient.getUserProfile(userId),
        (comment, authorProfile) ->
            ResponseEntity.ok(ApiResponse.success(
                CommentResponse.builder()
                    .commentId(comment.getCommentId())
                    .author(AuthorResponse.builder()
                        .id(comment.getAuthorId())
                        .nickname(authorProfile.getNickname())
                        .isMe(true)
                        .build())
                    .reply(ReplyResponse.builder()
                        .count(comment.getReply().getCount())
                        .build())
                    .content(comment.getContent())
                    .like(LikeResponse.builder()
                        .count(comment.getLike().getCount())
                        .isUserLike(comment.getLike().getIsUserLike())
                        .build())
                    .createdAt(comment.getCreatedAt())
                    .updatedAt(comment.getUpdatedAt())
                    .deletedAt(comment.getDeletedAt())
                    .build()
            ))
    );
  }
}
