package com.ninedocs.serviceaggregator.client.article.usercategoryquery;

import com.ninedocs.serviceaggregator.client.article.usercategoryquery.dto.UserCategoryQueryResponse;
import com.ninedocs.serviceaggregator.client.common.dto.DomainResponse;
import com.ninedocs.serviceaggregator.client.common.error.Unknown2xxErrorException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserCategoryQueryClient {

  private static final String DOMAIN_NAME = "Article";

  private final WebClient articleWebClient;

  public Mono<UserCategoryQueryResponse> getUserCategories(Long userId) {
    final String uriPath = "/api/v1/article/user-category/" + userId + "/";

    return articleWebClient.get()
        .uri(uriBuilder -> uriBuilder
            .path(uriPath)
            .build())
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<DomainResponse<UserCategoryQueryResponse>>() {
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
