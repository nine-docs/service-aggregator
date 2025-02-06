package com.ninedocs.serviceaggregator.client.user.profile;

import com.ninedocs.serviceaggregator.client.common.dto.DomainResponse;
import com.ninedocs.serviceaggregator.client.common.error.Unknown2xxErrorException;
import com.ninedocs.serviceaggregator.client.user.profile.dto.UserProfileBulkDto;
import com.ninedocs.serviceaggregator.client.user.profile.dto.UserProfileBulkRequest;
import com.ninedocs.serviceaggregator.client.user.profile.dto.UserProfileResponse;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserProfileBulkQueryClient {

  private static final String DOMAIN_NAME = "user";
  private static final String URI_PATH = "/api/v1/user/bulk-get";

  private final WebClient userWebClient;

  public Mono<UserProfileBulkDto> getUserProfiles(List<Long> userIds) {
    return userWebClient.post()
        .uri(uriBuilder -> uriBuilder.path(URI_PATH)
            .build())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(UserProfileBulkRequest.builder()
            .userIds(userIds)
            .build())
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(
            new ParameterizedTypeReference<DomainResponse<Map<Long, UserProfileResponse>>>() {
            })
        .flatMap(responseBody -> {
          if (!responseBody.getSuccess()) {
            return Mono.error(new Unknown2xxErrorException(
                DOMAIN_NAME, URI_PATH, responseBody.getErrorCode()
            ));
          }
          return Mono.just(new UserProfileBulkDto(responseBody.getData()));
        });
  }
}
