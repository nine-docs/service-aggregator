package com.ninedocs.serviceaggregator.client.user.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(UserWebClientProperties.class)
@RequiredArgsConstructor
public class UserWebClientConfig {

  private final WebClient.Builder webClientBuilder;

  @Bean
  public WebClient userWebClient(UserWebClientProperties properties) {
    return webClientBuilder
        .baseUrl(properties.getUrl())
        .build();
  }
}
