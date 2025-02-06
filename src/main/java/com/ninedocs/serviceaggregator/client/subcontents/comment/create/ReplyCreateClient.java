package com.ninedocs.serviceaggregator.client.subcontents.comment.create;

import com.ninedocs.serviceaggregator.client.common.dto.DomainResponse;
import com.ninedocs.serviceaggregator.client.common.error.Unknown2xxErrorException;
import com.ninedocs.serviceaggregator.client.subcontents.comment.create.dto.ReplyCreateClientRequest;
import com.ninedocs.serviceaggregator.client.subcontents.comment.create.dto.ReplyCreateClientResponse;
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
public class ReplyCreateClient {

  private static final String DOMAIN_NAME = "sub-contents";
  private static final String URI_PATH = "/api/v1/subcontents/reply";

  private final WebClient subContentsWebClient;

  public Mono<ReplyCreateClientResponse> createReply(ReplyCreateClientRequest request) {
    return subContentsWebClient.post()
        .uri(uriBuilder -> uriBuilder
            .path(URI_PATH)
            .build())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<DomainResponse<ReplyCreateClientResponse>>() {
        })
        .flatMap(domainResponse -> {
          if (!domainResponse.getSuccess()) {
            return Mono.error(
                new Unknown2xxErrorException(DOMAIN_NAME, URI_PATH, domainResponse.getErrorCode())
            );
          }
          return Mono.just(domainResponse.getData());
        });
  }
}
