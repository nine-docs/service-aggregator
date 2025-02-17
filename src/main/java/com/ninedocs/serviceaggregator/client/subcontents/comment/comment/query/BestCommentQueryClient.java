package com.ninedocs.serviceaggregator.client.subcontents.comment.comment.query;

import com.ninedocs.serviceaggregator.client.common.dto.DomainResponse;
import com.ninedocs.serviceaggregator.client.common.error.Unknown2xxErrorException;
import com.ninedocs.serviceaggregator.client.subcontents.comment.comment.query.dto.BestCommentRequest;
import com.ninedocs.serviceaggregator.client.subcontents.comment.comment.query.dto.CommentClientResponse;
import com.ninedocs.serviceaggregator.client.subcontents.comment.comment.query.dto.CommentCursorResponse;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class BestCommentQueryClient {

  private static final String DOMAIN_NAME = "sub-contents";
  private static final String URI_PATH = "/api/v1/subcontents/comment/list/like-sort";

  private final WebClient subContentsWebClient;

  public Mono<List<CommentClientResponse>> getBestComments(BestCommentRequest request) {
    return subContentsWebClient.get()
        .uri(uriBuilder -> {
          uriBuilder = uriBuilder
              .path(URI_PATH)
              .queryParam("articleId", request.getArticleId())
              .queryParam("limit", request.getLimit());
          uriBuilder = Objects.nonNull(request.getUserId())
              ? uriBuilder.queryParam("userId", request.getUserId())
              : uriBuilder;
          return uriBuilder.build();
        })
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<DomainResponse<CommentCursorResponse>>() {
        })
        .flatMap(domainResponse -> {
          if (!domainResponse.getSuccess()) {
            log.debug("# error code : {}", domainResponse.getErrorCode());
            return Mono.error(
                new Unknown2xxErrorException(DOMAIN_NAME, URI_PATH, domainResponse.getErrorCode())
            );
          }
          return Mono.just(domainResponse.getData().getItems());
        });
  }
}
