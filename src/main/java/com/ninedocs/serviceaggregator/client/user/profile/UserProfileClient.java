package com.ninedocs.serviceaggregator.client.user.profile;

import com.ninedocs.serviceaggregator.client.common.dto.DomainResponse;
import com.ninedocs.serviceaggregator.client.common.error.Unknown2xxErrorException;
import com.ninedocs.serviceaggregator.client.user.profile.dto.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserProfileClient {

  private final WebClient userWebClient;

  public Mono<UserProfileResponse> userProfile(Long userId) {
    return userWebClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/api/v1/user/" + userId)
            .build())
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<DomainResponse<UserProfileResponse>>() {
        })
        .flatMap(responseBody -> !responseBody.getSuccess()
            ? Mono.error(new Unknown2xxErrorException())
            : Mono.just(responseBody.getData()));
  }
}
