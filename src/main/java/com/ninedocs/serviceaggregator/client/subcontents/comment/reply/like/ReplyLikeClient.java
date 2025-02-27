package com.ninedocs.serviceaggregator.client.subcontents.comment.reply.like;

import com.ninedocs.serviceaggregator.client.common.dto.DomainResponse;
import com.ninedocs.serviceaggregator.client.common.error.Unknown2xxErrorException;
import com.ninedocs.serviceaggregator.client.subcontents.comment.reply.like.dto.ReplyLikeClientRequest;
import com.ninedocs.serviceaggregator.client.subcontents.comment.reply.like.exception.AlreadyLikedReplyException;
import com.ninedocs.serviceaggregator.client.subcontents.comment.reply.query.dto.ReplyCursorResponse.ReplyClientResponse;
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
public class ReplyLikeClient {

  private static final String DOMAIN_NAME = "sub-contents";
  private static final String URI_PATH = "/api/v1/subcontents/reply/like";

  private final WebClient subContentsWebClient;

  public Mono<ReplyClientResponse> likeReply(ReplyLikeClientRequest request) {
    log.debug("# replyId {}", request.getReplyId());
    log.debug("# userId {}", request.getUserId());
    return subContentsWebClient.post()
        .uri(uriBuilder -> uriBuilder
            .path(URI_PATH)
            .build())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<DomainResponse<ReplyClientResponse>>() {
        })
        .flatMap(domainResponse -> {
          if ("이미 추천한 답글입니다.".equals(domainResponse.getErrorCode())) {
            return Mono.error(new AlreadyLikedReplyException());
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
