package com.ninedocs.serviceaggregator.controller.article;

import com.ninedocs.serviceaggregator.client.article.articlequery.ArticleQueryClient;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "문제페이지")
@RestController
@RequiredArgsConstructor
public class ArticleController {

  private final ArticleQueryClient articleQueryClient;

  @Operation(summary = "문제 본문 조회")
  @GetMapping("/api/v1/article/{articleId}")
  public Mono<ResponseEntity<ApiResponse<String>>> getArticle(
      @PathVariable("articleId") Long articleId
  ) {
    return articleQueryClient.getArticle(articleId)
        .map(article -> ResponseEntity.ok(ApiResponse.success(
            article.getContents()
        )));
  }
}
