package com.ninedocs.serviceaggregator.client.user.unregister;

import com.ninedocs.serviceaggregator.client.common.dto.DomainResponse;
import com.ninedocs.serviceaggregator.client.common.error.Unknown2xxErrorException;
import com.ninedocs.serviceaggregator.client.user.unregister.dto.UnregisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UnregisterClient {

  private final WebClient userWebClient;

  public Mono<UnregisterResponse> unregister(Long userId) {
    final String uri = "/api/v1/user/" + userId;
    return userWebClient.delete()
        .uri(uri)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<DomainResponse<UnregisterResponse>>() {
        })
        .flatMap(domainResponse -> {
          if (!domainResponse.getSuccess()) {
            return Mono.error(
                new Unknown2xxErrorException("User", uri, domainResponse.getErrorCode()));
          }
          return Mono.just(domainResponse.getData());
        });
  }
}
