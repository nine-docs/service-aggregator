package com.ninedocs.serviceaggregator.client.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "domains.user")
@Getter
@RequiredArgsConstructor
public class UserWebClientProperties {

  private final String url;
}
