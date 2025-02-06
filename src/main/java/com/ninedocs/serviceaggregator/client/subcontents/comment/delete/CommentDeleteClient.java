package com.ninedocs.serviceaggregator.client.subcontents.comment.delete;

import com.ninedocs.serviceaggregator.client.common.dto.DomainResponse;
import com.ninedocs.serviceaggregator.client.common.error.Unknown2xxErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CommentDeleteClient {

  private static final String DOMAIN_NAME = "sub-contents";
  private static final String URI_PATH = "/api/v1/subcontents/comment";

  private final WebClient subContentsWebClient;

  public Mono<Void> deleteComment(Long commentId, Long userId) {
    return subContentsWebClient.delete()
        .uri(uriBuilder -> uriBuilder
            .path(URI_PATH)
            .queryParam("commentId", commentId)
            .queryParam("userId", userId)
            .build())
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<DomainResponse<Void>>() {
        })
        .flatMap(domainResponse -> {
          if (!domainResponse.getSuccess()) {
            return Mono.error(
                new Unknown2xxErrorException(DOMAIN_NAME, URI_PATH, domainResponse.getErrorCode()));
          }
          return Mono.empty();
        });
  }
}
