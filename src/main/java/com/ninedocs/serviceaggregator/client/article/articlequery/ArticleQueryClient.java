package com.ninedocs.serviceaggregator.client.article.articlequery;

import com.ninedocs.serviceaggregator.client.article.articlequery.dto.ArticleQueryClientResponse;
import com.ninedocs.serviceaggregator.client.common.dto.DomainResponse;
import com.ninedocs.serviceaggregator.client.common.error.Unknown2xxErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ArticleQueryClient {

  private static final String DOMAIN_NAME = "Article";

  private final WebClient articleWebClient;

  public Mono<ArticleQueryClientResponse> getArticle(Long articleId) {
    final String uriPath = "/api/v1/article/article/" + articleId + "/";

    return articleWebClient.get()
        .uri(uriPath)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<DomainResponse<ArticleQueryClientResponse>>() {
        })
        .flatMap(domainResponse -> {
          if (!domainResponse.getSuccess()) {
            return Mono.error(
                new Unknown2xxErrorException(DOMAIN_NAME, uriPath, domainResponse.getErrorCode())
            );
          }
          return Mono.just(domainResponse.getData());
        });
  }
}
