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

  private final WebClient articleWebClient;

  public Mono<List<CategoryResult>> getAllCategories() {
    final String uriPath = "/api/v1/article/category/";
    return articleWebClient.get()
        .uri(uriPath)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<DomainResponse<List<CategoryResult>>>() {
        })
        .flatMap(responseBody -> !responseBody.getSuccess()
            ? Mono.error(new Unknown2xxErrorException(
                "article", uriPath, responseBody.getErrorCode()))
            : Mono.just(responseBody.getData())
        );
  }
}
