package com.ninedocs.serviceaggregator.client.subcontents.comment.comment.like;

import com.ninedocs.serviceaggregator.client.common.dto.DomainResponse;
import com.ninedocs.serviceaggregator.client.common.error.Unknown2xxErrorException;
import com.ninedocs.serviceaggregator.client.subcontents.comment.comment.like.dto.CommentLikeClientRequest;
import com.ninedocs.serviceaggregator.client.subcontents.comment.comment.like.dto.CommentLikeClientResponse;
import com.ninedocs.serviceaggregator.client.subcontents.comment.comment.like.exception.CommentAlreadyLikedException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CommentLikeClient {

  private static final String DOMAIN_NAME = "sub-contents";
  private static final String URI_PATH = "/api/v1/subcontents/comment/like";

  private final WebClient subContentsWebClient;

  public Mono<CommentLikeClientResponse> like(CommentLikeClientRequest request) {
    return subContentsWebClient.post()
        .uri(uriBuilder -> uriBuilder
            .path(URI_PATH)
            .build())
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<DomainResponse<CommentLikeClientResponse>>() {
        })
        .flatMap(domainResponse -> {
          if ("이미 추천한 댓글입니다.".equals(domainResponse.getErrorCode())) {
            return Mono.error(new CommentAlreadyLikedException());
          }
          if (!domainResponse.getSuccess()) {
            return Mono.error(
                new Unknown2xxErrorException(DOMAIN_NAME, URI_PATH, domainResponse.getErrorCode())
            );
          }
          return Mono.just(domainResponse.getData());
        });
  }
}
