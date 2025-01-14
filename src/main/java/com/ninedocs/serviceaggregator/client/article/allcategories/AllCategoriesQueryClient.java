package com.ninedocs.serviceaggregator.client.article.allcategories;

import com.ninedocs.serviceaggregator.client.article.allcategories.dto.CategoryResult;
import com.ninedocs.serviceaggregator.client.common.dto.DomainResponse;
import com.ninedocs.serviceaggregator.client.common.error.Unknown2xxErrorException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AllCategoriesQueryClient {

  private static final String DOMAIN_NAME = "Article";
  private static final String URI_PATH = "/api/v1/article/category/";

  private final WebClient articleWebClient;

  public Mono<List<CategoryResult>> getAllCategories() {
    return articleWebClient.get()
        .uri(URI_PATH)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<DomainResponse<List<CategoryResult>>>() {
        })
        .flatMap(responseBody -> !responseBody.getSuccess()
            ? Mono.error(new Unknown2xxErrorException(
                DOMAIN_NAME, URI_PATH, responseBody.getErrorCode()))
            : Mono.just(responseBody.getData())
        );
  }
}
