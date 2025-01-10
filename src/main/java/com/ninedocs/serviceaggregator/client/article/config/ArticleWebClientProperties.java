package com.ninedocs.serviceaggregator.client.article.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "domains.article")
@Getter
@RequiredArgsConstructor
public class ArticleWebClientProperties {

  private final String url;
}
