package com.ninedocs.serviceaggregator.client.user.update;

import com.ninedocs.serviceaggregator.client.common.dto.DomainResponse;
import com.ninedocs.serviceaggregator.client.common.error.Unknown2xxErrorException;
import com.ninedocs.serviceaggregator.client.user.update.dto.PasswordUpdateClientRequest;
import com.ninedocs.serviceaggregator.client.user.update.exception.PasswordNotChangedException;
import com.ninedocs.serviceaggregator.client.user.update.exception.WrongPasswordException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdatePasswordClient {

  private static final String DOMAIN_NAME = "user";

  private final WebClient userWebClient;

  public final Mono<Void> updatePassword(
      Long userId, PasswordUpdateClientRequest request
  ) {
    String uriPath = "/api/v1/user/" + userId + "/password";
    return userWebClient.put()
        .uri(uriPath)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<DomainResponse<Void>>() {
        })
        .flatMap(domainResponse -> {
          if ("WRONG_PASSWORD".equals(domainResponse.getErrorCode())) {
            return Mono.error(new WrongPasswordException());
          }
          if ("PASSWORD_NOT_CHANGED".equals(domainResponse.getErrorCode())) {
            return Mono.error(new PasswordNotChangedException());
          }
          if (!domainResponse.getSuccess()) {
            return Mono.error(
                new Unknown2xxErrorException(DOMAIN_NAME, uriPath, domainResponse.getErrorCode())
            );
          }
          return Mono.empty();
        });
  }
}
