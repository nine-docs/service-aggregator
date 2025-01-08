package com.ninedocs.serviceaggregator.client.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientBuilderConfig {

  @Bean
  public WebClient.Builder webClientBuilder(
      Jackson2JsonEncoder jackson2JsonEncoder, Jackson2JsonDecoder jackson2JsonDecoder
  ) {
    return WebClient.builder()
        .codecs(configurer -> {
          configurer.defaultCodecs().jackson2JsonEncoder(jackson2JsonEncoder);
          configurer.defaultCodecs().jackson2JsonDecoder(jackson2JsonDecoder);
        });
  }
}
