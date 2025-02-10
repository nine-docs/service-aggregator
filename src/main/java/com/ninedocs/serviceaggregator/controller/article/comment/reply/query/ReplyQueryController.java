package com.ninedocs.serviceaggregator.controller.article.comment.reply.query;

import com.ninedocs.serviceaggregator.application.auth.JwtDecoder;
import com.ninedocs.serviceaggregator.client.subcontents.comment.reply.query.ReplyQueryClient;
import com.ninedocs.serviceaggregator.client.subcontents.comment.reply.query.dto.ReplyCursorResponse.ReplyClientResponse;
import com.ninedocs.serviceaggregator.client.subcontents.comment.reply.query.dto.ReplyQueryRequest;
import com.ninedocs.serviceaggregator.client.user.profile.UserProfileBulkQueryClient;
import com.ninedocs.serviceaggregator.client.user.profile.dto.UserProfileBulkDto;
import com.ninedocs.serviceaggregator.controller.article.comment.common.AuthorResponse;
import com.ninedocs.serviceaggregator.controller.article.comment.reply.common.dto.ReplyResponse;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import com.ninedocs.serviceaggregator.controller.common.response.CursorPageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Objects;
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
public class ReplyQueryController {

  private final JwtDecoder jwtDecoder;
  private final ReplyQueryClient replyQueryClient;
  private final UserProfileBulkQueryClient userProfileBulkQueryClient;

  @Operation(summary = "대댓글 목록 조회 페이지네이션")
  @GetMapping("/api/v1/article/{articleId}/comment/{commentId}/replies")
  public Mono<ResponseEntity<ApiResponse<CursorPageResponse<ReplyResponse, Long>>>> getReplies(
      @PathVariable Long articleId,
      @PathVariable Long commentId,
      @Parameter(description = "이전 페이지 마지막 대댓글 (첫페이지 조회 시 null)") @RequestParam(required = false) Long cursor,
      @Parameter(description = "페이지 당 아이템 최대 갯수") @RequestParam(defaultValue = "20") int limit,
      @Parameter(description = "내 댓글 여부 계산 용 토큰, 생략 가능") @RequestHeader(value = "Authentication", required = false) String authToken
  ) {
    final Long userId = jwtDecoder.decodeWithoutException(authToken).getUserId();

    return replyQueryClient.getReplies(
            ReplyQueryRequest.builder()
                .commentId(commentId)
                .cursor(cursor == null ? 0L : cursor)
                .limit(limit)
                .build()
        )
        .flatMap(replyCursorResponse -> {
          List<Long> authorIds = replyCursorResponse.getItems().stream()
              .map(ReplyClientResponse::getAuthorId)
              .toList();
          return userProfileBulkQueryClient.getUserProfiles(authorIds)
              .map(userProfiles -> toReplyResponse(
                  replyCursorResponse.getItems(), userProfiles, userId
              ))
              .map(replies -> ResponseEntity.ok(ApiResponse.success(CursorPageResponse.of(
                  replies,
                  CollectionUtils.isEmpty(replies)
                      ? null
                      : replies.get(replies.size() - 1).getReplyId()
              ))));
        });
  }

  private List<ReplyResponse> toReplyResponse(
      List<ReplyClientResponse> replyClientResponses,
      UserProfileBulkDto userProfileBulkDto,
      Long userId
  ) {
    return replyClientResponses.stream()
        .map(reply -> ReplyResponse.builder()
            .replyId(reply.getReplyId())
            .author(AuthorResponse.builder()
                .id(reply.getAuthorId())
                .nickname(userProfileBulkDto.getNicknameByUserId(
                    reply.getAuthorId(), "알수없는 사용자"
                ))
                .isMe(Objects.equals(reply.getAuthorId(), userId))
                .build())
            .content(reply.getContent())
            .createdAt(reply.getCreatedAt())
            .updatedAt(reply.getUpdatedAt())
            .build())
        .toList();
  }
}
