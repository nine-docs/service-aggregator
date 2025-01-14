package com.ninedocs.serviceaggregator.client.article.userscheduleupsert;

import com.ninedocs.serviceaggregator.client.article.userscheduleupsert.dto.UserScheduleUpsertRequest;
import com.ninedocs.serviceaggregator.client.article.userscheduleupsert.dto.UserScheduleUpsertResponse;
import com.ninedocs.serviceaggregator.client.common.dto.DomainResponse;
import com.ninedocs.serviceaggregator.client.common.error.Unknown2xxErrorException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserScheduleUpsertClient {

  private static final String DOMAIN_NAME = "article";
  private static final String URI_PATH = "/api/v1/article/user-schedule/";

  private final WebClient articleWebClient;

  public Mono<List<UserScheduleUpsertResponse>> upsertUserSchedule(
      UserScheduleUpsertRequest request
  ) {
    return articleWebClient.post()
        .uri(uriBuilder -> uriBuilder
            .path(URI_PATH)
            .build())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .retrieve()
        .bodyToMono(
            new ParameterizedTypeReference<DomainResponse<List<UserScheduleUpsertResponse>>>() {}
        )
        .flatMap(domainResponse -> !domainResponse.getSuccess()
            ? Mono.error(new Unknown2xxErrorException(
                DOMAIN_NAME, URI_PATH, domainResponse.getErrorCode()))
            : Mono.just(domainResponse.getData()));
  }
}
