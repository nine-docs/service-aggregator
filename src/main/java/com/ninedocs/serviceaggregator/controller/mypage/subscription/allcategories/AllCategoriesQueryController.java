package com.ninedocs.serviceaggregator.controller.mypage.subscription.allcategories;

import com.ninedocs.serviceaggregator.client.article.allcategories.AllCategoriesQueryClient;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import com.ninedocs.serviceaggregator.controller.mypage.subscription.allcategories.dto.AllCategoriesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "구독관리")
@Slf4j
@RestController
@RequiredArgsConstructor
public class AllCategoriesQueryController {

  private final AllCategoriesQueryClient client;

  @Operation(summary = "모든 카테고리 목록 조회")
  @GetMapping("/api/v1/my-page/subscription/all-categories")
  public Mono<ResponseEntity<ApiResponse<AllCategoriesResponse>>> getAllCategories() {
    return client.getAllCategories()
        .map(domainResponse -> ApiResponse.success(AllCategoriesResponse.of(domainResponse)))
        .map(ResponseEntity::ok);
  }
}
