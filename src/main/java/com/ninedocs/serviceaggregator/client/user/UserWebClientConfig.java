package com.ninedocs.serviceaggregator.client.user;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(UserWebClientProperties.class)
public class UserWebClientConfig {

  @Bean
  public WebClient userWebClient(UserWebClientProperties properties) {
    return WebClient.builder()
        .baseUrl(properties.getUrl())
        .build();
  }
}
