package com.ninedocs.serviceaggregator.client.user.emailverificationcode;

import com.ninedocs.serviceaggregator.client.common.dto.DomainResponse;
import com.ninedocs.serviceaggregator.client.user.emailverificationcode.dto.EmailVerificationCodeRequest;
import com.ninedocs.serviceaggregator.client.user.emailverificationcode.dto.EmailVerificationCodeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EmailVerificationCodeClient {

  private final WebClient userWebClient;

  public Mono<DomainResponse<EmailVerificationCodeResponse>> sendEmailVerificationCode(String email) {
    EmailVerificationCodeRequest requestBody = EmailVerificationCodeRequest.builder()
        .email(email)
        .build();

    return userWebClient.post()
        .uri(uriBuilder -> uriBuilder
            .path("/api/v1/user/email-verification-code")
            .build())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<>() {});
  }
}
