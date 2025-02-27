package com.ninedocs.serviceaggregator.client.subcontents.comment.reply.query;

import com.ninedocs.serviceaggregator.client.common.dto.DomainResponse;
import com.ninedocs.serviceaggregator.client.common.error.Unknown2xxErrorException;
import com.ninedocs.serviceaggregator.client.subcontents.comment.reply.query.dto.ReplyCursorResponse;
import com.ninedocs.serviceaggregator.client.subcontents.comment.reply.query.dto.ReplyQueryRequest;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ReplyQueryClient {

  private static final String DOMAIN_NAME = "sub-contents";
  private static final String URI_PATH = "/api/v1/subcontents/reply/list";

  private final WebClient subContentsWebClient;

  public Mono<ReplyCursorResponse> getReplies(ReplyQueryRequest request) {
    return subContentsWebClient.get()
        .uri(uriBuilder -> {
              uriBuilder = uriBuilder
                  .path(URI_PATH)
                  .queryParam("commentId", request.getCommentId())
                  .queryParam("cursor", request.getCursor())
                  .queryParam("limit", request.getLimit());
              if (Objects.nonNull(request.getUserId())) {
                uriBuilder = uriBuilder.queryParam("userId", request.getUserId());
              }
              return uriBuilder.build();
            }
        )
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<DomainResponse<ReplyCursorResponse>>() {
        })
        .flatMap(domainResponse -> {
          if (!domainResponse.getSuccess()) {
            return Mono.error(
                new Unknown2xxErrorException(DOMAIN_NAME, URI_PATH, domainResponse.getErrorCode()));
          }
          return Mono.just(domainResponse.getData());
        });
  }
}
