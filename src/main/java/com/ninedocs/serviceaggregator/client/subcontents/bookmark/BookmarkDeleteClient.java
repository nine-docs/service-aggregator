package com.ninedocs.serviceaggregator.client.subcontents.bookmark;

import com.ninedocs.serviceaggregator.client.common.dto.DomainResponse;
import com.ninedocs.serviceaggregator.client.common.error.Unknown2xxErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BookmarkDeleteClient {

  private static final String DOMAIN_NAME = "sub-contents";

  private final WebClient subContentsWebClient;

  public Mono<Void> deleteBookmark(Long bookmarkId, Long userId) {
    final String uriPath = "/api/v1/bookmark/" + userId + "/" + bookmarkId;

    return subContentsWebClient.delete()
        .uri(uriBuilder -> uriBuilder
            .path(uriPath)
            .build())
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<DomainResponse<Void>>() {
        })
        .flatMap(domainResponse -> {
          if (!domainResponse.getSuccess()) {
            return Mono.error(
                new Unknown2xxErrorException(DOMAIN_NAME, uriPath, domainResponse.getErrorCode())
            );
          }
          return Mono.empty();
        });
  }
}
