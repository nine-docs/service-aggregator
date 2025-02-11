package com.ninedocs.serviceaggregator.client.subcontents.comment.reply.like;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class ReplyLikeClient {

  private static final String DOMAIN_NAME = "sub-contents";
  private static final String URI_PATH = "/api/v1/subcontents/comment/like";

  private final WebClient subContentsWebClient;
}
