package com.ninedocs.serviceaggregator.client.subcontents.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "domains.sub-contents")
@Getter
@RequiredArgsConstructor
public class SubContentsWebClientProperties {

  private final String url;
}
