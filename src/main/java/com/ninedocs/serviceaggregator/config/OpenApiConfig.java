package com.ninedocs.serviceaggregator.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("Service Aggregator")
            .version("1.0"));
  }

  @Bean
  public GroupedOpenApi registerGroupedOpenApi() {
    return GroupedOpenApi.builder()
        .group("(a) 회원가입")
        .pathsToMatch("/api/v1/register/**")
        .build();
  }

  @Bean
  public GroupedOpenApi myPageGroupedOpenApi() {
    return GroupedOpenApi.builder()
        .group("(b) 마이페이지")
        .pathsToMatch("/api/v1/my-page/**")
        .build();
  }


  @Bean
  public GroupedOpenApi systemGroupedOpenApi() {
    return GroupedOpenApi.builder()
        .group("(z) System")
        .pathsToMatch("/api/v1/system/**")
        .build();
  }
}
