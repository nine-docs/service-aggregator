package com.ninedocs.serviceaggregator.client.subcontents.comment.comment.query;

import com.ninedocs.serviceaggregator.client.common.dto.DomainResponse;
import com.ninedocs.serviceaggregator.client.common.error.Unknown2xxErrorException;
import com.ninedocs.serviceaggregator.client.subcontents.comment.comment.query.dto.CommentCursorResponse;
import com.ninedocs.serviceaggregator.client.subcontents.comment.comment.query.dto.CommentQueryRequest;
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
public class CommentQueryClient {

  private static final String DOMAIN_NAME = "sub-contents";
  private static final String URI_PATH = "/api/v1/subcontents/comment/list";

  private final WebClient subContentsWebClient;

  public Mono<CommentCursorResponse> getComments(CommentQueryRequest request) {
    return subContentsWebClient.get()
        .uri(uriBuilder -> {
          uriBuilder = uriBuilder
              .path(URI_PATH)
              .queryParam("articleId", request.getArticleId())
              .queryParam("cursor", request.getCursor())
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
            return Mono.error(
                new Unknown2xxErrorException(DOMAIN_NAME, URI_PATH, domainResponse.getErrorCode())
            );
          }
          return Mono.just(domainResponse.getData());
        });
  }
}
