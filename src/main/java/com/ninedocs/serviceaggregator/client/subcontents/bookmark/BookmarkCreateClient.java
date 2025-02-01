package com.ninedocs.serviceaggregator.client.subcontents.bookmark;

import com.ninedocs.serviceaggregator.client.common.dto.DomainResponse;
import com.ninedocs.serviceaggregator.client.common.error.Unknown2xxErrorException;
import com.ninedocs.serviceaggregator.client.subcontents.bookmark.dto.BookmarkCreateClientRequest;
import com.ninedocs.serviceaggregator.client.subcontents.bookmark.dto.BookmarkCreateClientResponse;
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

  public Mono<BookmarkCreateClientResponse> createBookmark(Long userId, Long articleId) {
    final String uriPath = "/api/v1/bookmark/" + userId;

    return subContentsWebClient.post()
        .uri(uriBuilder -> uriBuilder
            .path(uriPath)
            .build())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(BookmarkCreateClientRequest.builder()
            .articleId(articleId)
            .build())
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<DomainResponse<BookmarkCreateClientResponse>>() {
        })
        .flatMap(domainResponse -> {
          if (!domainResponse.getSuccess()) {
            // Todo API 수정되면 반영할것
            return Mono.error(
                new Unknown2xxErrorException(DOMAIN_NAME, uriPath, domainResponse.getErrorCode())
            );
          }
          return Mono.just(domainResponse.getData());
        });
  }
}
