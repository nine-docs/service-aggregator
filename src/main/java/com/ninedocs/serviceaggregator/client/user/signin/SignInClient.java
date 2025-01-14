package com.ninedocs.serviceaggregator.client.user.signin;

import com.ninedocs.serviceaggregator.client.common.dto.DomainResponse;
import com.ninedocs.serviceaggregator.client.user.signin.dto.SignInRequest;
import com.ninedocs.serviceaggregator.client.user.signin.dto.SignInResponse;
import com.ninedocs.serviceaggregator.controller.login.exception.LoginFailedException;
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

  public Mono<SignInResponse> signIn(SignInRequest request) {
    return userWebClient.post()
        .uri(uriBuilder -> uriBuilder
            .path("/api/v1/user/login")
            .build())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<DomainResponse<SignInResponse>>() {
        })
        .flatMap(domainResponse -> {
          if ("LOGIN_FAILED".equals(domainResponse.getErrorCode())) {
            return Mono.error(new LoginFailedException());
          }
          return Mono.just(domainResponse.getData());
        });
  }
}
