package com.ninedocs.serviceaggregator.controller.article.comment.create;

import com.ninedocs.serviceaggregator.controller.article.comment.common.dto.CommentResponse;
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

  @Operation(summary = "댓글 작성")
  @PostMapping("/api/v1/article/{articleId}/comment")
  public Mono<ResponseEntity<ApiResponse<CommentResponse>>> create(
      @PathVariable Long articleId,
      @RequestHeader("Authentication") String authToken,
      @RequestBody CommentCreateRequest request
  ) {
    return Mono.just(ResponseEntity.status(HttpStatus.CREATED.value())
        .body(ApiResponse.success(
            CommentResponse.builder().build()
        )));
  }
}
