package com.ninedocs.serviceaggregator.client.article.userscheduleupsert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class UserScheduleUpsertClient {

  private final WebClient articleWebClient;
}
