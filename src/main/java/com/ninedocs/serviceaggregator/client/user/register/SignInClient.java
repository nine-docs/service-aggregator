package com.ninedocs.serviceaggregator.client.user.register;

import com.ninedocs.serviceaggregator.client.user.common.dto.DomainResponse;
import com.ninedocs.serviceaggregator.client.user.register.dto.SignUpRequest;
import com.ninedocs.serviceaggregator.client.user.register.dto.SignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SignInClient {

  private final WebClient userWebClient;

  public Mono<DomainResponse<SignUpResponse>> signIn(SignUpRequest request) {
    return userWebClient.post()
        .uri(uriBuilder -> uriBuilder
            .path("/api/v1/user")
            .build())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<>() {
        });
  }
}
