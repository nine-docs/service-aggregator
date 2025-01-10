package com.ninedocs.serviceaggregator.client.user.signup;

import com.ninedocs.serviceaggregator.client.common.dto.DomainResponse;
import com.ninedocs.serviceaggregator.client.user.signup.dto.SignUpRequest;
import com.ninedocs.serviceaggregator.client.user.signup.dto.SignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SignUpClient {

  private final WebClient userWebClient;

  public Mono<DomainResponse<SignUpResponse>> signUp(SignUpRequest request) {
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
