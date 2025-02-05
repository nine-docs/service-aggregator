package com.ninedocs.serviceaggregator.client.subcontents.bookmark;

import com.ninedocs.serviceaggregator.client.common.dto.DomainResponse;
import com.ninedocs.serviceaggregator.client.common.error.Unknown2xxErrorException;
import com.ninedocs.serviceaggregator.client.subcontents.bookmark.dto.BookmarkIdResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookmarkQueryClient {

  private static final String DOMAIN_NAME = "sub-contents";
  private static final String URI_PATH = "/api/v1/subcontents/bookmark/exists";

  private final WebClient subContentsWebClient;

  public Mono<Optional<BookmarkIdResponse>> getArticleBookmarkExist(
      Long userId, Long articleId
  ) {
    return subContentsWebClient.get()
        .uri(uriBuilder -> uriBuilder.path(URI_PATH)
            .queryParam("userId", userId)
            .queryParam("articleId", articleId)
            .build())
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<DomainResponse<BookmarkIdResponse>>() {
        })
        .flatMap(domainResponse -> {
          log.debug("# data : {}", domainResponse.getData());
          if (!domainResponse.getSuccess()) {
            return Mono.error(
                new Unknown2xxErrorException(DOMAIN_NAME, URI_PATH, domainResponse.getErrorCode())
            );
          }
          return Mono.just(Optional.ofNullable(domainResponse.getData()));
        });
  }
}
