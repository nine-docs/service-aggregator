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
    return articleWebClient.get()
        .uri("/api/v1/article/category/")
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<DomainResponse<List<CategoryResult>>>() {
        })
        .flatMap(responseBody -> !responseBody.getSuccess()
            ? Mono.error(new Unknown2xxErrorException())
            : Mono.just(responseBody.getData())
        );
  }
}
