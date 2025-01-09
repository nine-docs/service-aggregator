package com.ninedocs.serviceaggregator.client.user.config;

import com.ninedocs.serviceaggregator.client.common.error.ApiErrorException;
import com.ninedocs.serviceaggregator.client.user.common.dto.UserErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
@EnableConfigurationProperties(UserWebClientProperties.class)
@RequiredArgsConstructor
public class UserWebClientConfig {

  private final WebClient.Builder webClientBuilder;

  @Bean
  public WebClient userWebClient(UserWebClientProperties properties) {
    return webClientBuilder
        .baseUrl(properties.getUrl())
        .filter(errorHandler())
        .build();
  }

  /*
   retrieve() 호출 이후 응답을 받으면
   그 다음 동작을 수행하기 전에 실행됨

   여기서 Mono.error() 가 return 되면
   호출 체인이 중단되고 예외가 호출부로 전파된다.
   */
  private ExchangeFilterFunction errorHandler() {
    return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
      if (clientResponse.statusCode().isError()) {
        return clientResponse.bodyToMono(UserErrorResponse.class)
            .flatMap(errorBody -> Mono.error(
                ApiErrorException.builder()
                    .domainName("User")
                    .requestUri(clientResponse.request().getURI())
                    .statusCode(clientResponse.statusCode())
                    .reason(errorBody.getMessage())
                    .build()
            ));
      }
      return Mono.just(clientResponse);
    });
  }
}
