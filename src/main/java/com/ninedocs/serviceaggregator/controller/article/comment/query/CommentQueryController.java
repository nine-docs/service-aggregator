package com.ninedocs.serviceaggregator.controller.article.comment.query;

import com.ninedocs.serviceaggregator.application.auth.JwtDecoder;
import com.ninedocs.serviceaggregator.client.subcontents.comment.query.CommentQueryClient;
import com.ninedocs.serviceaggregator.client.subcontents.comment.query.dto.CommentCursorResponse.CommentClientResponse;
import com.ninedocs.serviceaggregator.client.subcontents.comment.query.dto.CommentQueryRequest;
import com.ninedocs.serviceaggregator.client.user.profile.UserProfileBulkQueryClient;
import com.ninedocs.serviceaggregator.client.user.profile.dto.UserProfileBulkDto;
import com.ninedocs.serviceaggregator.controller.article.comment.common.dto.AuthorResponse;
import com.ninedocs.serviceaggregator.controller.article.comment.common.dto.CommentResponse;
import com.ninedocs.serviceaggregator.controller.article.comment.common.dto.CommentResponse.ReplyResponse;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import com.ninedocs.serviceaggregator.controller.common.response.CursorPageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "댓글/대댓글")
@RestController
@RequiredArgsConstructor
public class CommentQueryController {

  private final JwtDecoder jwtDecoder;
  private final CommentQueryClient commentQueryClient;
  private final UserProfileBulkQueryClient userProfileBulkQueryClient;

  @Operation(summary = "댓글 목록 조회 페이지네이션")
  @GetMapping("/api/v1/article/{articleId}/comments")
  public Mono<ResponseEntity<ApiResponse<CursorPageResponse<CommentResponse, Long>>>> getComments(
      @PathVariable Long articleId,
      @Parameter(description = "이전 페이지 마지막 댓글 (첫페이지 조회 시 null)") @RequestParam(required = false) Long cursor,
      @Parameter(description = "페이지 당 아이템 최대 갯수") @RequestParam(defaultValue = "20") int limit,
      @Parameter(description = "내 댓글 여부 계산 용 토큰, 생략 가능") @RequestHeader(value = "Authentication", required = false) String authToken
  ) {
    final Long userId = jwtDecoder.decodeWithoutException(authToken).getUserId();

    return commentQueryClient.getComments(
            CommentQueryRequest.builder()
                .articleId(articleId)
                .cursor(cursor == null ? 0L : cursor)
                .limit(limit)
                .build()
        )
        .flatMap(commentCursorResponse -> {
          List<Long> userIds = commentCursorResponse.getItems().stream()
              .map(CommentClientResponse::getAuthorId)
              .toList();

          return userProfileBulkQueryClient.getUserProfiles(userIds)
              .map(userProfiles ->
                  toCommentResponses(commentCursorResponse.getItems(), userProfiles, userId)
              )
              .map(comments ->
                  ResponseEntity.ok(ApiResponse.success(CursorPageResponse.of(
                      comments,
                      CollectionUtils.isEmpty(comments)
                          ? null
                          : comments.get(comments.size() - 1).getCommentId()
                  ))));
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
                .isMe(comment.getAuthorId().equals(userId))
                .build())
            .reply(ReplyResponse.builder()
                .count(comment.getReply().getCount())
                .build())
            .content(comment.getContent())
            .createdAt(comment.getCreatedAt())
            .updatedAt(comment.getUpdatedAt())
            .build())
        .toList();
  }
}
