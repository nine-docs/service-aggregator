package com.ninedocs.serviceaggregator.client.article.articlesummariesquery;

import com.ninedocs.serviceaggregator.client.article.articlesummariesquery.dto.ArticleSummariesDto;
import com.ninedocs.serviceaggregator.client.article.articlesummariesquery.dto.ArticleSummariesRequest;
import com.ninedocs.serviceaggregator.client.article.articlesummariesquery.dto.ArticleSummaryResponse;
import com.ninedocs.serviceaggregator.client.common.dto.DomainResponse;
import com.ninedocs.serviceaggregator.client.common.error.Unknown2xxErrorException;
import io.jsonwebtoken.lang.Collections;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ArticleSummariesQueryClient {

  private static final String DOMAIN_NAME = "Article";
  private static final String URI_PATH = "/api/v1/article/article/retrieve-multiple/";

  private final WebClient articleWebClient;

  public Mono<ArticleSummariesDto> getArticleSummaries(List<Long> articleIds) {
    if (Collections.isEmpty(articleIds)) {
      return Mono.just(ArticleSummariesDto.empty());
    }
    return articleWebClient.post()
        .uri(uriBuilder -> uriBuilder
            .path(URI_PATH)
            .build())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(ArticleSummariesRequest.builder()
            .articleIds(articleIds)
            .build())
        .retrieve()
        .bodyToMono(
            new ParameterizedTypeReference<DomainResponse<Map<Long, ArticleSummaryResponse>>>() {
            }
        )
        .flatMap(domainResponse -> {
          if (!domainResponse.getSuccess()) {
            return Mono.error(
                new Unknown2xxErrorException(DOMAIN_NAME, URI_PATH, domainResponse.getErrorCode()));
          }
          return Mono.just(new ArticleSummariesDto(domainResponse.getData()));
        });
  }
}
