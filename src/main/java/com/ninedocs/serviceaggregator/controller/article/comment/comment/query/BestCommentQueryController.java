package com.ninedocs.serviceaggregator.controller.article.comment.comment.query;

import com.ninedocs.serviceaggregator.application.auth.JwtDecoder;
import com.ninedocs.serviceaggregator.client.subcontents.comment.comment.query.BestCommentQueryClient;
import com.ninedocs.serviceaggregator.client.subcontents.comment.comment.query.dto.BestCommentRequest;
import com.ninedocs.serviceaggregator.client.subcontents.comment.comment.query.dto.CommentClientResponse;
import com.ninedocs.serviceaggregator.client.user.profile.UserProfileBulkQueryClient;
import com.ninedocs.serviceaggregator.client.user.profile.dto.UserProfileBulkDto;
import com.ninedocs.serviceaggregator.controller.article.comment.comment.common.dto.CommentResponse;
import com.ninedocs.serviceaggregator.controller.article.comment.comment.common.dto.CommentResponse.LikeResponse;
import com.ninedocs.serviceaggregator.controller.article.comment.comment.common.dto.CommentResponse.ReplyResponse;
import com.ninedocs.serviceaggregator.controller.article.comment.common.AuthorResponse;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "댓글")
@RestController
@RequiredArgsConstructor
public class BestCommentQueryController {

  private final JwtDecoder jwtDecoder;
  private final BestCommentQueryClient bestCommentQueryClient;
  private final UserProfileBulkQueryClient userProfileBulkQueryClient;

  @Operation(summary = "좋아요 많이 받은 댓글 목록 조회")
  @GetMapping("/api/v1/article/{articleId}/comments/best")
  public Mono<ResponseEntity<ApiResponse<List<CommentResponse>>>> getComments(
      @PathVariable Long articleId,
      @Parameter(description = "조회할 댓글 갯수") @RequestParam(defaultValue = "20") int limit,
      @Parameter(description = "내 댓글 여부 계산 용 토큰, 생략 가능") @RequestHeader(value = "Authentication", required = false) String authToken
  ) {
    final Long userId = jwtDecoder.decodeWithoutException(authToken).getUserId();

    return bestCommentQueryClient.getBestComments(
            BestCommentRequest.builder()
                .articleId(articleId)
                .userId(userId)
                .limit(limit)
                .build()
        )
        .flatMap(commentResponses -> {
          List<Long> authorIds = commentResponses.stream()
              .map(CommentClientResponse::getAuthorId)
              .toList();

          return userProfileBulkQueryClient.getUserProfiles(authorIds)
              .map(userProfiles -> toCommentResponses(
                  commentResponses, userProfiles, userId
              ))
              .map(comments ->
                  ResponseEntity.ok(ApiResponse.success(comments)));
        });
  }

  private List<CommentResponse> toCommentResponses(
      List<CommentClientResponse> commentClientResponses,
      UserProfileBulkDto userProfileBulkDto,
      Long userId
  ) {
    return commentClientResponses.stream()
        .map(comment -> CommentResponse.builder()
            .commentId(comment.getCommentId())
            .author(AuthorResponse.builder()
                .id(comment.getAuthorId())
                .nickname(userProfileBulkDto.getNicknameByUserId(
                    comment.getAuthorId(), "알수없는 사용자"
                ))
                .isMe(comment.getAuthorId() != null
                    && Objects.equals(comment.getAuthorId(), userId))
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
            .build())
        .toList();
  }
}
