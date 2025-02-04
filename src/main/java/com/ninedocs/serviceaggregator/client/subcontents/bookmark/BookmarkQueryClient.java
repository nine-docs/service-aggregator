package com.ninedocs.serviceaggregator.client.subcontents.bookmark;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class BookmarkQueryClient {

  private static final String DOMAIN_NAME = "sub-contents";

  private final WebClient subContentsWebClient;


}
