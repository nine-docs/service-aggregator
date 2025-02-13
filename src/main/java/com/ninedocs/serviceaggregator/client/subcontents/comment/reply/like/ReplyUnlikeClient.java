package com.ninedocs.serviceaggregator.client.subcontents.comment.reply.like;

import com.ninedocs.serviceaggregator.client.common.dto.DomainResponse;
import com.ninedocs.serviceaggregator.client.common.error.Unknown2xxErrorException;
import com.ninedocs.serviceaggregator.client.subcontents.comment.reply.like.dto.ReplyLikeClientRequest;
import com.ninedocs.serviceaggregator.client.subcontents.comment.reply.like.dto.ReplyLikeClientResponse;
import com.ninedocs.serviceaggregator.client.subcontents.comment.reply.like.exception.ReplyNotLikedException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ReplyUnlikeClient {

  private static final String DOMAIN_NAME = "sub-contents";
  private static final String URI_PATH = "/api/v1/subcontents/reply/like";

  private final WebClient subContentsWebClient;

  public Mono<ReplyLikeClientResponse> unlikeReply(ReplyLikeClientRequest request) {
    return subContentsWebClient.delete()
        .uri(uriBuilder -> uriBuilder
            .path(URI_PATH)
            .queryParam("replyId", request.getReplyId())
            .queryParam("userId", request.getUserId())
            .build())
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<DomainResponse<ReplyLikeClientResponse>>() {
        })
        .flatMap(domainResponse -> {
          if ("추천하지 않은 댓글입니다.".equals(domainResponse.getErrorCode())) {
            return Mono.error(new ReplyNotLikedException());
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
