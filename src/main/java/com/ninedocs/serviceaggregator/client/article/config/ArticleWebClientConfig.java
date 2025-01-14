package com.ninedocs.serviceaggregator.client.article.config;

import com.ninedocs.serviceaggregator.client.common.error.ApiErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
@EnableConfigurationProperties(ArticleWebClientProperties.class)
@RequiredArgsConstructor
@Slf4j
public class ArticleWebClientConfig {

  private final WebClient.Builder webClientBuilder;

  @Bean
  public WebClient articleWebClient(ArticleWebClientProperties properties) {
    return webClientBuilder
        .baseUrl(properties.getUrl())
        .filter(errorHandler())
        .build();
  }

  private ExchangeFilterFunction errorHandler() {
    return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
      log.debug("# clientResponse statusCode: {}", clientResponse.statusCode());
      if (clientResponse.statusCode().isError()) {
        return clientResponse.bodyToMono(String.class)
            .flatMap(errorBody -> Mono.error(
                ApiErrorException.builder()
                    .domainName("Article")
                    .requestUri(clientResponse.request().getURI())
                    .statusCode(clientResponse.statusCode())
                    .reason(errorBody)
                .build()
            ));
      }
      return Mono.just(clientResponse);
    });
  }
}
