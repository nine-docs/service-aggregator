package com.ninedocs.serviceaggregator.client.common.error;

import java.net.URI;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;

@Builder
@RequiredArgsConstructor
@Getter
public class ApiErrorException extends RuntimeException {

  private final String domainName;
  private final URI requestUri;
  private final HttpStatusCode statusCode;
  private final String reason;
}
