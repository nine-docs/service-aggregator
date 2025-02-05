package com.ninedocs.serviceaggregator.client.subcontents.bookmark;

import com.ninedocs.serviceaggregator.client.common.dto.DomainResponse;
import com.ninedocs.serviceaggregator.client.common.error.Unknown2xxErrorException;
import com.ninedocs.serviceaggregator.client.subcontents.bookmark.exception.BookmarkNotExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookmarkDeleteClient {

  private static final String DOMAIN_NAME = "sub-contents";

  private final WebClient subContentsWebClient;

  public Mono<Void> deleteBookmark(Long bookmarkId, Long userId) {
    final String uriPath = "/api/v1/subcontents/bookmark";

    return subContentsWebClient.delete()
        .uri(uriBuilder -> uriBuilder
            .path(uriPath)
            .queryParam("bookmarkId", bookmarkId)
            .queryParam("userId", userId)
            .build())
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<DomainResponse<Void>>() {
        })
        .flatMap(domainResponse -> {
          if ("존재하지 않는 북마크입니다.".equals(domainResponse.getErrorCode())) {
            return Mono.error(new BookmarkNotExistException());
          }
          if (!domainResponse.getSuccess() ) {
            log.debug("# error code : {}", domainResponse.getErrorCode());
            return Mono.error(
                new Unknown2xxErrorException(DOMAIN_NAME, uriPath, domainResponse.getErrorCode())
            );
          }
          return Mono.empty();
        });
  }
}
