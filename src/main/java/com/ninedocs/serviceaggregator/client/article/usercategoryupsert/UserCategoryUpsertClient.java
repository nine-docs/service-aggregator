package com.ninedocs.serviceaggregator.client.article.usercategoryupsert;

import com.ninedocs.serviceaggregator.client.article.usercategoryupsert.dto.UserCategoryUpsertRequest;
import com.ninedocs.serviceaggregator.client.article.usercategoryupsert.dto.UserCategoryUpsertResponse;
import com.ninedocs.serviceaggregator.client.common.dto.DomainResponse;
import com.ninedocs.serviceaggregator.client.common.error.Unknown2xxErrorException;
import java.util.List;
import java.util.stream.Collectors;
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
public class UserCategoryUpsertClient {

  private final WebClient articleWebClient;

  public Mono<List<UserCategoryUpsertResponse>> upsertUserCategory(
      UserCategoryUpsertRequest request
  ) {
    log.debug("# userEmail : {}", request.getUserEmail());
    log.debug("# categories : {}", request.getCategoryIds().stream()
        .map(String::valueOf)
        .collect(Collectors.joining(", "))
    );
    final String uriPath = "/api/v1/article/user-category/";

    return articleWebClient.post()
        .uri(uriBuilder -> uriBuilder
            .path(uriPath)
            .build())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .retrieve()
        .bodyToMono(
            new ParameterizedTypeReference<DomainResponse<List<UserCategoryUpsertResponse>>>() {
            })
        .flatMap(domainResponse -> !domainResponse.getSuccess()
            ? Mono.error(new Unknown2xxErrorException(
                "article", uriPath, domainResponse.getErrorCode()))
            : Mono.just(domainResponse.getData()));
  }
}
