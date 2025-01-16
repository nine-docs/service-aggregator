package com.ninedocs.serviceaggregator.client.article.userschedulequery;

import com.ninedocs.serviceaggregator.client.article.userschedulequery.dto.UserScheduleQueryResponse;
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
public class UserScheduleQueryClient {

  private static final String DOMAIN_NAME = "Article";

  private final WebClient articleWebClient;

  public Mono<UserScheduleQueryResponse> getUserSchedules(Long userId) {
    final String uriPath = "/api/v1/article/user-schedule/" + userId + "/";

    return articleWebClient.get()
        .uri(uriBuilder -> uriBuilder
            .path(uriPath)
            .build())
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<DomainResponse<UserScheduleQueryResponse>>() {
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
