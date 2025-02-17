package com.ninedocs.serviceaggregator.client.user.update;

import com.ninedocs.serviceaggregator.client.common.dto.DomainResponse;
import com.ninedocs.serviceaggregator.client.common.error.Unknown2xxErrorException;
import com.ninedocs.serviceaggregator.client.user.update.dto.UpdateNicknameClientRequest;
import com.ninedocs.serviceaggregator.client.user.update.dto.UpdateNicknameClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UpdateNicknameClient {

  private static final String DOMAIN_NAME = "user";

  private final WebClient userWebClient;

  public final Mono<UpdateNicknameClientResponse> updateNickname(Long userId, String newNickname) {
    String uriPath = "/api/v1/user/" + userId + "/nickname";
    return userWebClient.put()
        .uri(uriPath)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .bodyValue(UpdateNicknameClientRequest.builder()
            .newNickname(newNickname)
            .build())
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<DomainResponse<UpdateNicknameClientResponse>>() {
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
