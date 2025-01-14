package com.ninedocs.serviceaggregator.client.article.usercategoryupsert;

import com.ninedocs.serviceaggregator.client.article.usercategoryupsert.dto.UserCategoryUpsertRequest;
import com.ninedocs.serviceaggregator.client.common.dto.DomainResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserCategoryUpsertClient {

  private final WebClient articleWebClient;

  public Mono<DomainResponse<?>> upsertUserCategory(
      UserCategoryUpsertRequest request
  ) {
    return Mono.empty();
  }
}
