package com.ninedocs.serviceaggregator.client.subcontents.bookmark;

import com.ninedocs.serviceaggregator.client.common.dto.DomainResponse;
import com.ninedocs.serviceaggregator.client.subcontents.bookmark.dto.BookmarkCreateClientRequest;
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
public class BookmarkCreateClient {

  private static final String DOMAIN_NAME = "sub-contents";

  private final WebClient subContentsWebClient;

  // Todo 북마크 API 수정되면 반영해야함
  public Mono<String> createBookmark(Long userId, Long articleId) {
    final String uri = "/api/v1/bookmark/" + userId;

    return subContentsWebClient.post()
        .uri(uriBuilder -> uriBuilder
            .path(uri)
            .build())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(BookmarkCreateClientRequest.builder()
            .articleId(articleId)
            .build())
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<DomainResponse<String>>() {
        })
        .flatMap(domainResponse -> {
          log.debug(domainResponse.getData());
          return Mono.just(domainResponse.getData());
        });
  }
}
