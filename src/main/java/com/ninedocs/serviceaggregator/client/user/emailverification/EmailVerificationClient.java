package com.ninedocs.serviceaggregator.client.user.emailverification;

import com.ninedocs.serviceaggregator.client.common.dto.DomainResponse;
import com.ninedocs.serviceaggregator.client.user.emailverification.dto.EmailVerificationRequest;
import com.ninedocs.serviceaggregator.client.user.emailverification.dto.EmailVerificationResponse;
import com.ninedocs.serviceaggregator.controller.register.emailverification.exception.EmailVerificationFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EmailVerificationClient {

  private final WebClient userWebClient;

  public Mono<EmailVerificationResponse> verifyEmail(
      EmailVerificationRequest request
  ) {
    return userWebClient.post()
        .uri(uriBuilder -> uriBuilder
            .path("/api/v1/user/email-verification")
            .build())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<DomainResponse<EmailVerificationResponse>>() {
        })
        .flatMap(domainResponse -> {
          if ("VERIFICATION_FAILED".equals(domainResponse.getErrorCode())) {
            return Mono.error(new EmailVerificationFailedException());
          }
          return Mono.just(domainResponse.getData());
        });
  }
}
